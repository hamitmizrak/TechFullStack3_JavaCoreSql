package com.hamitmizrak.javase.service;

import com.hamitmizrak.javase.controller.RegisterController;
import com.hamitmizrak.javase.dto.RegisterDto;
import com.hamitmizrak.javase.files.FilePathData;
import com.hamitmizrak.javase.roles.ERoles;

import java.util.Scanner;

// Sifre masking
// Enum Array
// Rollere göre giriş yap
// Kullanıcı aktif mi ona göre giriş yap
public class RegisterLoginServices {

    // Injection
    private RegisterController registerController=new RegisterController();
    private FilePathData filePathData=new FilePathData();

    // Eğer sistemde ilgili email ile kullanıcı varsa sisteme giriş yapsın
    // Eğer sistemde ilgili email yoksa register olsun

    private String[] allRoles(){
      /*  for ( String temp :ERoles.valueOf() ) {
        }*/
        return null;
    }

    // REGISTER
    private RegisterDto register(){
        Scanner klavye=new Scanner(System.in);
        RegisterDto registerDto=new RegisterDto();
        String uNickname,  uEmailAddress,  uPassword,  rolles;
        Long remainingNumber;
        Boolean isPassive;
        System.out.println("\n###REGISTER SAYSASINA HOSGELDINIZ");
        System.out.println("Takma adınızı giriniz");
        uNickname=klavye.nextLine();
        System.out.println("Emailinizi giriniz");
        uEmailAddress=klavye.nextLine();
        System.out.println("Sifrenizi giriniz");
        uPassword=klavye.nextLine();
        // default rol user olacak
        rolles= ERoles.USER.getValue().toString();
        remainingNumber=5L;
        isPassive=true;
        ///////////////////
        registerDto.setuNickname(uNickname);
        registerDto.setuEmailAddress(uEmailAddress);
        registerDto.setuPassword(uPassword);
        registerDto.setRolles(rolles);
        registerDto.setRemainingNumber(remainingNumber);
        registerDto.setPassive(isPassive);
        registerController.create(registerDto);
        return registerDto;
    }

    // LOGIN
    private RegisterDto login(){
        Scanner klavye=new Scanner(System.in);
        RegisterDto registerDto=new RegisterDto();
        String   uEmailAddress,  uPassword;
        Long remaingNumber=0L;
        System.out.println("\n###LOGIN SAYSASINA HOSGELDINIZ");
        System.out.println("Emailinizi giriniz");
        uEmailAddress=klavye.nextLine();
        System.out.println("Sifrenizi giriniz");
        uPassword=klavye.nextLine();

        // Email Find
        RegisterDto registerEmailFind=registerController.findByEmail(uEmailAddress);
        // Kullanıcı yoksa kayıt olsun ve logşn sayfasına ageri donsun.
        if(registerEmailFind==null){
            // eğer kullanıcı yoksa kayıt olsun
            register();
            // Kayot olduktan sonra Login sayfasına geri dön
            login();
        } else{
            // Eğer Kullanıcı Pasifse
            if(registerEmailFind.getPassive()==false){
                System.out.println("Üyeliğiniz Pasif edilmiştir sisteme giriş yapamazsınız");
                System.out.println("Lütfen admin'e başvurunuz.");
                System.exit(0);
            }

            // Eğer kullanıcı varsa sisteme giriş yapsın
            if(uEmailAddress.equals(registerEmailFind.getuEmailAddress()) && uPassword.equals(registerEmailFind.getuPassword())){
                adminProcess();
            }else{
                // Kullanıcının kalan hakkı
                remaingNumber=registerEmailFind.getRemainingNumber();
                remaingNumber--;
                registerEmailFind.setRemainingNumber(remaingNumber);
                System.out.println("Kalan Hakkınız: "+registerEmailFind.getRemainingNumber());
                System.out.println("Sifreniz veya Emailiniz yanlış girdiniz");
                // Kalan Hak Database Eksilt
                registerController.updateRemaing(remaingNumber,registerEmailFind);
                // File Loglama yapsın
                filePathData.logFileWriter(uEmailAddress,uPassword);
                if(remaingNumber==0){
                    System.out.println("Giriş hakkınız kalmadı Hesanız Bloke oldu");
                    System.out.println("Admine Başvuru yapınız");
                    System.exit(0);
                }else if(remaingNumber>=1){
                    login();
                }
            } //end else
        }
        return registerDto;
    }

    private void adminProcess(){
        System.out.println("ADMIN SAYFASINA HOSGELDINIZ");
    }

    public static void main(String[] args) {
        RegisterLoginServices services=new RegisterLoginServices();
        //services.register();
        services.login();
    }
} //end class
