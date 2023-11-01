package com.hamitmizrak.dto;

import java.io.Serializable;

// Register
public class RegisterDto extends BaseDto implements Serializable {

    // Serileştirme
    public static final Long serialVersionUID=1L;

    //Variable
    private String uNickname;
    private String uEmailAddress;
    private String uPassword;
    private Long remainingNumber; // Kullanıcı kalan hak
    private Boolean isPassive;
    private String rolles;


    // constructor(parametresiz)
    public RegisterDto() {
    }

    // constructor(parametreli)
    public RegisterDto(String uNickname, String uEmailAddress, String uPassword, Long remainingNumber, Boolean isPassive, String rolles) {
        this.uNickname = uNickname;
        this.uEmailAddress = uEmailAddress;
        this.uPassword = uPassword;
        this.remainingNumber = remainingNumber;
        this.isPassive = isPassive;
        this.rolles = rolles;
    }

    //toString
    @Override
    public String toString() {
        return "RegisterDto{" +
                "uNickname='" + uNickname + '\'' +
                ", uEmailAddress='" + uEmailAddress + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", remainingNumber=" + remainingNumber +
                ", isPassive=" + isPassive +
                ", rolles='" + rolles + '\'' +
                "} " + super.toString();
    }

    @Override
    public String nowDateAbstract() {
        return null;
    }

    //GETTER AND SETTER
    public String getuNickname() {
        return uNickname;
    }

    // Eğer Kullanıcının kelimelrde başında ve sonunda boşluklar varsa bunu engelememiz gerekiyor.
    public void setuNickname(String uNickname) {
        this.uNickname = uNickname.trim();
    }

    public String getuEmailAddress() {
        return uEmailAddress;
    }

    public void setuEmailAddress(String uEmailAddress) {
        this.uEmailAddress = uEmailAddress.trim();
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword.trim();
    }

    public Long getRemainingNumber() {
        return remainingNumber;
    }

    public void setRemainingNumber(Long remainingNumber) {
        this.remainingNumber = remainingNumber;
    }

    public Boolean getPassive() {
        return isPassive;
    }

    public void setPassive(Boolean passive) {
        isPassive = passive;
    }

    public String getRolles() {
        return rolles;
    }

    public void setRolles(String rolles) {
        this.rolles = rolles;
    }

} //end class RegisterDto
