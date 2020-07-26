package mk.ukim.finki.bnks_project.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import mk.ukim.finki.bnks_project.model.AuctionItem;
import mk.ukim.finki.bnks_project.model.Bid;
import mk.ukim.finki.bnks_project.model.BidDto;
import mk.ukim.finki.bnks_project.model.User;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchSimpleKeyException;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchUserException;
import mk.ukim.finki.bnks_project.service.AuctionItemService;
import mk.ukim.finki.bnks_project.service.BidService;
import mk.ukim.finki.bnks_project.service.UserService;
import mk.ukim.finki.bnks_project.service.kdc.KDCService;
import mk.ukim.finki.bnks_project.utils.JwtTokenUtil;
import mk.ukim.finki.bnks_project.utils.ReqUtils;
import mk.ukim.finki.bnks_project.utils.VerificationUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/auction/", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class SignedAuctionAPI {
    private final AuctionItemService itemService;
    private final UserService userService;
    private final BidService bidService;
    private final KDCService KDC;

    public SignedAuctionAPI(AuctionItemService itemService, UserService userService, BidService bidService, KDCService kdc) {
        this.itemService = itemService;
        this.userService = userService;
        this.bidService = bidService;
        KDC = kdc;
    }

    @GetMapping("auctions")
    public List<AuctionItem> getAllAuctionItems(HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {
        //Validate JWT token
        String token = request.getHeader(JwtTokenUtil.header_name);
        if(token == null) response.sendError(401,"No JWT token specified. User not authenticated.");
        try {
        boolean valid = false;
        valid = userService.validateToken(token);

        //Get all auction items
        if(valid)
            return itemService.getAllItems();
        else
            response.sendError(401,"JWT token not valid. User not authenticated.");
        } catch (NoSuchUserException e) {
            e.printStackTrace();
            response.sendError(500,"Invalid username");
        }
        return new ArrayList<>();
    }
    @GetMapping("bids")
    public List<BidDto> getAllBidsForUser(HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        //Validate JWT token
        String token = request.getHeader(JwtTokenUtil.header_name);
        if(token == null) response.sendError(401,"No JWT token specified. User not authenticated.");
        try {
            boolean valid = false;
            valid = userService.validateToken(token);
            String user = userService.getUsernameFromToken(token);
            //Get all auction items
            if(valid)
            {
                return bidService.getBidsForUser(user);
            }
            else
                response.sendError(401,"JWT token not valid. User not authenticated.");
        } catch (NoSuchUserException e) {
            e.printStackTrace();
            response.sendError(500,"Invalid username");
        }
        return new ArrayList<>();
    }

    @PostMapping("place_bid")
    public BidDto placeBid(@RequestBody BidDto bid, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        //Validate JWT token
        String token = request.getHeader(JwtTokenUtil.header_name);
        if(token == null) response.sendError(401,"No JWT token specified. User not authenticated.");

        boolean valid = false;
        try {
            valid = userService.validateToken(token);
        } catch (NoSuchUserException e) {
            e.printStackTrace();
            response.sendError(401,"Invalid token username");
        }
        if(!valid){
            response.sendError(401,"JWT token not valid. User not authenticated.");
        }
        //Is the bid you want to post valid -> deal with this on front end
        //Bid in body
        ObjectMapper mapper = new ObjectMapper();
        String bodyBid = mapper.writeValueAsString(bid).replace("value\":","value\":\"").replace("}","\"}");
        String method = request.getMethod().toUpperCase();
        String path = request.getRequestURI().substring(request.getContextPath().length());
        //Now validate signature
        String xTimestamp = request.getHeader("x-timestamp");
        String xAlgorithm = request.getHeader("x-algorithm");
        String xKeyID = request.getHeader("x-keyid");
        String xSignature = request.getHeader("x-signature");

        if(xTimestamp == null || xAlgorithm == null || xKeyID == null || xSignature == null){
            response.sendError(401,"The request doesn't contain all necessary signature headers");
        }
        String signatureString = ReqUtils.recreateSignature(xAlgorithm,method,path,xTimestamp,bodyBid);

        //get key from KDC
        String username = userService.getUsernameFromToken(token);
        try {
            String fetchedKeyFromKDC = KDC.getKey(Long.valueOf(xKeyID),username);
            //verify using key and signatur STring (compare if equal to xSignature
            boolean isSignatureVerified = VerificationUtils.verifyHMAC(xSignature,signatureString,fetchedKeyFromKDC);
            if(isSignatureVerified){
                AuctionItem au = this.itemService.findById(bid.getAuction_id());
                User u = this.userService.findUserById(bid.getUser_id());
                Bid bidNew = new Bid(bid.getValue(),au,u);
                this.bidService.saveBid(bidNew);
                this.itemService.addBidToAuctionItem(au,bidNew);
                return bidNew.getBidDto();
            }
            else{
                response.sendError(401,"Signature wasn't verified");
            }
        } catch (NoSuchSimpleKeyException e) {
            e.printStackTrace();
            response.sendError(401,"KDC failed in retrieving key with id: "+xKeyID+" for user: "+username);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(401,"An error occurred while verifying signature.");
        }
        return null;
    }

}
