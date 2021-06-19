package com.ensias.moroccan_cars.services;

import com.ensias.moroccan_cars.Dto.ClaimDto;
import com.ensias.moroccan_cars.models.Claim;
import com.ensias.moroccan_cars.models.User;
import com.ensias.moroccan_cars.repositories.ClaimRepository;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClaimService {
    private final ClaimRepository claimRepository;
    private final EmailService emailService;


    public ClaimService(ClaimRepository claimRepository, EmailService emailService) {
        this.claimRepository = claimRepository;

        this.emailService = emailService;
    }

    public Claim saveClaim(Claim claim){
        claim = claimRepository.save(claim);

        String text = "Claim from user : <b>"+claim.getUser().getFirstName() + " " +claim.getUser().getLastName()+"</b><br><br>Email : <b>"+ claim.getUser().getEmail()+"</b><br><br><br><br>";
        text +=claim.getBody();
        text += "<br><br><br>sent at : <font color=green> "+claim.getDate()+"</font>";

        List<String> emails = claimRepository.getAllRespEmails();
        String[] emails_arr = new String[emails.size()];
        int i=0;
        for (String email : emails) {
            emails_arr[i] = email;
            i++;
        }
        Claim finalClaim = claim;
        String finalText = text;
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                emailService.sendSimpleEmail(finalClaim.getSubject(), finalText,emails_arr);
            }
        }).start();

        return claim;
    }
    public List<ClaimDto> findAllClaims(){
        List<ClaimDto> claimDtos = new ArrayList<>();
        List<Claim> claims = claimRepository.findAll();
        for (Claim claim : claims) {
            claimDtos.add(new ClaimDto(claim));
        }
        return claimDtos;
    }
    public List<ClaimDto> findClaimByUser(int user_id) throws NoSuchElementException {

        List<ClaimDto> claimDtos = new ArrayList<>();
        List<Claim> claims = claimRepository.findAllByUser(user_id);
        for (Claim claim : claims) {
            claimDtos.add(new ClaimDto(claim));
        }
        return claimDtos;
    }
    public void treatClaim(boolean treated,int claim_id){
        Date treatDate = null;
        if(treated){
            treatDate = new Date();
        }
        Claim claim = claimRepository.findById(claim_id).get();
        claim.setTreatmentDate(treatDate);
        claimRepository.save(claim);

        if(treated){
            final Date finalTreatDate = treatDate;
            final  String userFullNale = claim.getUser().getFirstName()+" " +claim.getUser().getLastName();
            final String claim_subject =  claim.getSubject();
            final String user_email = ""+claim.getUser().getEmail();
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    String text = "Hello <strong>Mr."+userFullNale+"</strong>";
                    text+="<br/> <br/>";
                    text += "Your claim <font color = green>\"" + claim_subject +"\"</font> is treated <br/> <br/>";
                    text +="treatement date : " + finalTreatDate;
                    emailService.sendSimpleEmail("Your claim \""+ claim_subject +"\" is treated",text,user_email);
                }
            }).start();

        }
    }
    public Claim deleteClaim(int id) throws NoSuchElementException{
        try{
            Claim claim = claimRepository.findById(id).get();
            claimRepository.delete(claim);
            return claim;
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("Claim not found");
        }
    }
    public Claim deleteUserClaim(int id , User user) throws NoSuchElementException{
        try{
            Claim claim = claimRepository.findById(id).get();
            if(claim.getUser().getId()!=user.getId())  throw new NoSuchElementException("Claim not found");
            claimRepository.delete(claim);
            return claim;
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("Claim not found");
        }
    }
    public List<Claim> findClaimsByUser(User user){
        List<Claim> claims = claimRepository.findAllByUser(user.getId());
        return claims;
    }
    public Claim findUserClaim(int claim_id,User user){
        try{
            Claim claim = claimRepository.findById(claim_id).get();
            if(claim.getUser().getId()!=user.getId())  throw new NoSuchElementException("Claim not found");
            return claim;
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("Claim not found");
        }
    }
}
