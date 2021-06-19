package com.ensias.moroccan_cars.services;

import com.ensias.moroccan_cars.Dto.RentRequestDto;
import com.ensias.moroccan_cars.models.*;
import com.ensias.moroccan_cars.repositories.RentRepository;
import com.ensias.moroccan_cars.repositories.RentRequestRepo;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class RentService {
    private final RentRepository rentRepository;
    private final RentRequestRepo rentRequestRepo;
    private final EmailService emailService;

    public RentService(RentRepository rentRepository, RentRequestRepo rentRequestRepo, EmailService emailService) {
        this.rentRepository = rentRepository;
        this.rentRequestRepo = rentRequestRepo;
        this.emailService = emailService;
    }
    public RentRequest createRentRequest(RentRequest rentRequest){
        rentRequest = rentRequestRepo.save(rentRequest);
        rentRequest.setStatus(rentRequestRepo.findStatusById(rentRequest.getStatus().getId()));
        rentRequest.setVehicule(rentRequestRepo.findVehiculeByRequest(rentRequest));
        final RentRequest request = rentRequest;
        final List<String> emails = rentRequestRepo.getAllRespEmails();
        final Vehicule vehicule = request.getVehicule();
        final User user = request.getUser();
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy : hh:mm:ss");
                String subject = "Rent request from " + user.getFirstName() + " " + user.getLastName();
                String text = "You've got a rent request from  <strong>"+ user.getFirstName() + " " + user.getLastName() +"</strong> with email <font color = blue>"+user.getEmail()+"</font>";
                text += "<br/><br/>User Requested an <b>"+vehicule.getOwner() + " " + vehicule.getModel() + "</b> between <font color = green>"+df.format(request.getDateStart()) + "</font> and <font color = green>"+df.format(request.getDateEnd())+"</font>";
                text += "<br/><br/> Request Date : <font color = blue>" + df2.format(request.getDate());
                String[] emails_arr = new String[emails.size()];
                int i= 0;
                for (String email : emails) {
                    emails_arr[i] = email;
                    i++;
                }
                emailService.sendSimpleEmail(subject,text,emails_arr);
            }
        }).start();
        return rentRequest;
    }
    public List<RentRequestDto> getAllRentRequests(){
        List<RentRequest> rentRequests  =  rentRequestRepo.findAll();
        List<RentRequestDto> rentRequestDtos = new ArrayList<>();
        for (RentRequest rentRequest : rentRequests) {
            rentRequestDtos.add(new RentRequestDto(rentRequest));
        }
        return rentRequestDtos;
    }

    public List<RentRequestDto> getUserRentRequests(User user){
        List<RentRequest> rentRequests  =  rentRequestRepo.findRentRequestByUser(user);
        List<RentRequestDto> rentRequestDtos = new ArrayList<>();
        for (RentRequest rentRequest : rentRequests) {
            rentRequestDtos.add(new RentRequestDto(rentRequest));
        }
        return rentRequestDtos;
    }
    public RentRequest treatRentRequest(int request_id,int status_id){
        RentRequest request = rentRequestRepo.findById(request_id).get();
        if(!"RENTED".equals(request.getStatus().getName())) {
            Status s = rentRequestRepo.findStatusById(status_id);
            request.setStatus(s);
            request = rentRequestRepo.save(request);
            if("RENTED".equals(request.getStatus().getName())){
                Rent rent = new Rent(0,request.getDateStart(),request.getDateEnd(),request.getUser(),request.getVehicule());
                rent = rentRepository.save(rent);
            }
        }

        final RentRequest rentRequest = request;
        new Thread(
                new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        String subject = "";
                        String text = "";
                        String email = rentRequest.getUser().getEmail();
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                        float price = ChronoUnit.DAYS.between(rentRequest.getDateStart().toInstant(),rentRequest.getDateEnd().toInstant())*rentRequest.getVehicule().getPrice();
                        if("ACCEPTED".equals(rentRequest.getStatus().getName())){
                            text += "Hello <strong> Mr." +rentRequest.getUser().getFirstName() + " "+rentRequest.getUser().getLastName()+"</strong>";
                            text += "<br/><br/>Your rent request for the car <strong>"+rentRequest.getVehicule().getOwner()+" "+rentRequest.getVehicule().getModel()+"</strong> has been accepted";
                            text +="<br/><br/>your total price is: "+price +" MAD";
                            text +="<br/><br/>Visit us to take your car";
                            subject  = "Your rent request has been accepted";
                            emailService.sendSimpleEmail(subject,text,email);


                        }if("REFUSED".equals(rentRequest.getStatus().getName())){

                            text += "Hello <strong> Mr." +rentRequest.getUser().getFirstName() + " "+rentRequest.getUser().getLastName()+"</strong>";
                            text += "<br/><br/>Your rent request for the car <strong>"+rentRequest.getVehicule().getOwner()+" "+rentRequest.getVehicule().getModel()+"</strong> has been Refused";
                            text +="<br/><br/>You can send us claims in our website or visit us if you want";
                            subject  = "Your rent request has been refused";
                            emailService.sendSimpleEmail(subject,text,email);

                        }if("RENTED".equals(rentRequest.getStatus().getName())){
                            text += "Hello <strong> Mr." +rentRequest.getUser().getFirstName() + " "+rentRequest.getUser().getLastName()+"</strong>";
                            text += "<br/><br/>You successfully rented the car <strong>"+rentRequest.getVehicule().getOwner()+" "+rentRequest.getVehicule().getModel()+"</strong>";
                            text += "<br/><br/>Your rent starts at  <font color=blue>"+df.format(rentRequest.getDateStart())+"</font>";
                            text += " and ends at   <font color=blue>"+df.format(rentRequest.getDateEnd())+"</font>";
                            text +="<br/><br/>Thank you for using our services, if you have any claims don't hesitate to send them in our website";
                            subject  = "You rented a car";
                            emailService.sendSimpleEmail(subject,text,email);

                        }

                    }
                }
        ).start();
        return request;
    }

    public List<Rent> getAllRents(){
        return rentRepository.findAll();
    }
}
