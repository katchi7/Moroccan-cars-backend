package com.ensias.moroccan_cars.Dto;

import com.ensias.moroccan_cars.models.Authorities;
import com.ensias.moroccan_cars.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Slf4j
public class UserDto extends RepresentationModel<UserDto> {

    public UserDto(){}

    public UserDto(User user){
        this.authorities = user.getAuthority();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.adresse = user.getAdr();
        this.email = user.getEmail();
        this.image = user.getImage();
        this.numTel = user.getNum();
    }

    @NotNull
    @Size(min = 5)
    private String firstName;
    @NotNull
    @Size(min = 5)
    private String lastName;

    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$" )
    private String email;

    private String adresse;
    private String numTel;
    private String image;
    @NotNull
    @Size(min = 8)
    private String password;

    private Authorities authorities;

    public User User(){
        authorities = new Authorities();
        authorities.setId(1);
        User user = new User(0,firstName,lastName,email,adresse,numTel,image,password,authorities);
        return user;
    }
}