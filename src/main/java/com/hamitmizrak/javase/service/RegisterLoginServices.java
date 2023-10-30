package com.hamitmizrak.javase.service;

import com.hamitmizrak.javase.controller.RegisterController;
import com.hamitmizrak.javase.dto.RegisterDto;
import com.hamitmizrak.javase.exception.HamitMizrak0Exception;
import com.hamitmizrak.javase.files.FilePathData;
import com.hamitmizrak.javase.roles.ERoles;

import java.util.Scanner;

// Sifre masking
// Enum Array
// Rollere göre giriş yap

public class RegisterLoginServices {

    // Injection
    private RegisterController registerController = new RegisterController();
    private FilePathData filePathData = new FilePathData();

    // Eğer sistemde ilgili email ile kullanıcı varsa sisteme giriş yapsın
    // Eğer sistemde ilgili email yoksa register olsun

    private String[] allRoles() {
      /*  for ( String temp :ERoles.valueOf() ) {
        }*/
        return null;
    }

    // REGISTER
    private RegisterDto register() {
        Scanner klavye = new Scanner(System.in);
        RegisterDto registerDto = new RegisterDto();
        String uNickname, uEmailAddress, uPassword, rolles;
        Long remainingNumber;
        Boolean isPassive;
        System.out.println("\n###REGISTER SAYSASINA HOSGELDINIZ");
        System.out.println("Takma adınızı giriniz");
        uNickname = klavye.nextLine();
        System.out.println("Emailinizi giriniz");
        uEmailAddress = klavye.nextLine();
        System.out.println("Sifrenizi giriniz");
        uPassword = klavye.nextLine();
        // default rol user olacak
        rolles = ERoles.USER.getValue().toString();
        remainingNumber = 5L;
        isPassive = true;
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
    public RegisterDto login() {
        Scanner klavye = new Scanner(System.in);
        RegisterDto registerDto = new RegisterDto();
        String uEmailAddress, uPassword;
        Long remaingNumber = 0L;
        System.out.println("\n###LOGIN SAYSASINA HOSGELDINIZ");
        System.out.println("Emailinizi giriniz");
        uEmailAddress = klavye.nextLine();
        System.out.println("Sifrenizi giriniz");
        uPassword = klavye.nextLine();

        // Email Find
        RegisterDto registerEmailFind = registerController.findByEmail(uEmailAddress);
        // Kullanıcı yoksa kayıt olsun ve logşn sayfasına ageri donsun.
        if (registerEmailFind == null) {
            // eğer kullanıcı yoksa kayıt olsun
            register();
            // Kayot olduktan sonra Login sayfasına geri dön
            login();
        } else {
            // Eğer Kullanıcı Pasifse
            if (registerEmailFind.getPassive() == false) {
                System.out.println("Üyeliğiniz Pasif edilmiştir sisteme giriş yapamazsınız");
                System.out.println("Lütfen admin'e başvurunuz.");
                System.exit(0);
            }

            // Eğer kullanıcı varsa sisteme giriş yapsın
            if (uEmailAddress.equals(registerEmailFind.getuEmailAddress()) && uPassword.equals(registerEmailFind.getuPassword())) {
                adminProcess(registerEmailFind);
            } else {
                // Kullanıcının kalan hakkı
                remaingNumber = registerEmailFind.getRemainingNumber();
                remaingNumber--;
                registerEmailFind.setRemainingNumber(remaingNumber);
                System.out.println("Kalan Hakkınız: " + registerEmailFind.getRemainingNumber());
                System.out.println("Sifreniz veya Emailiniz yanlış girdiniz");
                // Kalan Hak Database Eksilt
                registerController.updateRemaing(remaingNumber, registerEmailFind);
                // File Loglama yapsın
                filePathData.logFileWriter(uEmailAddress, uPassword);
                if (remaingNumber == 0) {
                    System.out.println("Giriş hakkınız kalmadı Hesanız Bloke oldu");
                    System.out.println("Admine Başvuru yapınız");
                    System.exit(0);
                } else if (remaingNumber >= 1) {
                    login();
                }
            } //end else
        }
        return registerDto;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    private void adminProcess(RegisterDto registerDto) {
        Scanner klavye = new Scanner(System.in);
        while (true) {
            System.out.println("\nADMIN SAYFASINA HOSGELDINIZ");
            System.out.println("Lütfen Seçiminizi Yapınız");
            System.out.println("1-) Üye Listele\n2-) Üye Ekle\n3-) Üye Bul(ID)\n4-) Üye Bul (Email)");
            System.out.println("5-) Üye Güncelle\n6-) Üye Sil\n7-) Giriş Logları\n8-) Çıkış Yap");
            int chooise = klavye.nextInt();
            switch (chooise) {
                case 0:
                    System.out.println("Home Page");
                    specialHomePage();
                    break;
                case 1:
                    System.out.println("Listeleme");
                    memberList();
                    break;
                case 2:
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        System.out.println("Oluşturma");
                        RegisterDto registerDtoCreate = memberCreate();
                        System.out.println(registerDtoCreate);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 3:
                    if ( registerDto.getRolles().equals(ERoles.ADMIN.getValue()) ||  registerDto.getRolles().equals(ERoles.WRITER.getValue())) {
                        memberList();
                        System.out.println("ID'e göre Bulma");
                        RegisterDto registerDtoFindId = memberFindById();
                        /*if(registerDto.getId()==registerDtoFindId.getId()){
                            System.out.println(registerController.findById(registerDto.getId()));
                        }
                        else{
                            System.out.println(registerDtoFindId);
                        }*/
                        System.out.println(registerDtoFindId);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 4:
                    if ( registerDto.getRolles().equals(ERoles.ADMIN.getValue()) || registerDto.getRolles().equals(ERoles.WRITER.getValue())) {
                        memberList();
                        System.out.println("Email'e göre bulma");
                        RegisterDto registerDtoFindEmail = memberfindEmail();
                        System.out.println(registerDtoFindEmail);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 5:
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        memberList();
                        System.out.println("Güncelleme");
                        RegisterDto registerDtoUpdate = memberUpdate();
                        System.out.println(registerDtoUpdate);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 6:
                    if (registerDto.getRolles().equals(ERoles.ADMIN.getValue())) {
                        memberList();
                        System.out.println("Silme");
                        RegisterDto registerDtoDelete = memberDelete();
                        System.out.println(registerDtoDelete);
                    } else {
                        System.out.println("Rolünüz: " + registerDto.getRolles() + " Yetkiniz yoktur");
                        //throw new HamitMizrak0Exception("Yetkiniz Yoktur");
                    }
                    break;
                case 7:
                    logFile();
                    break;
                case 8:
                    logout();
                    break;
                default:
                    System.out.println("Lütfen belirtilen aralıkta sayı giriniz");
                    break;
            } //end switch
        } //end while
    } //end method adminProcess


    // just member login
    private void specialHomePage() {
    }


    // CRUD
    // LIST
    private void memberList() {
        registerController.list().forEach(System.out::println);
    }

    // CREATE
    private RegisterDto memberCreate() {
        return register();
    }

    // Find Id
    private RegisterDto memberFindById() {
        System.out.println("Lütfen Bulmak istediğiniz ID giriniz");
        return registerController.findById(new Scanner(System.in).nextLong());
    }

    // Find Email
    private RegisterDto memberfindEmail() {
        System.out.println("Lütfen Bulmak istediğiniz email giriniz");
        return registerController.findByEmail(new Scanner(System.in).nextLine());
    }

    // Update
    private RegisterDto memberUpdate() {
        Scanner klavye = new Scanner(System.in);
        RegisterDto registerDto = new RegisterDto();
        String uNickname, uEmailAddress, uPassword, rolles;
        Long remainingNumber, id;
        Boolean isPassive;
        System.out.println("Güncellemek istediğiniz ID  giriniz");
        id = klavye.nextLong();
        // NOT: Scanner'da tam sayıdan sonra String Gelirse bir alt satıra geçiyor
        // bunu engellemenin yolu klavye.nextLine()
        klavye.nextLine();

        System.out.println("Güncellemek istediğiniz Takma adınızı giriniz");
        uNickname = klavye.nextLine();
        System.out.println("Güncellemek istediğiniz Emailinizi giriniz");
        uEmailAddress = klavye.nextLine();
        System.out.println("Güncellemek istediğiniz Sifrenizi giriniz");
        uPassword = klavye.nextLine();
        // default rol user olacak
        rolles = ERoles.USER.getValue().toString();
        System.out.println("Güncellemek istediğiniz hak sayısını giriniz");
        remainingNumber = klavye.nextLong();
        System.out.println("Güncellemek istediğiniz kullanıcı aktif/pasif");
        isPassive = true;
        ////////////////////////////////////////////////////////////////////
        registerDto.setId(id);
        registerDto.setuNickname(uNickname);
        registerDto.setuEmailAddress(uEmailAddress);
        registerDto.setuPassword(uPassword);
        registerDto.setRolles(rolles);
        registerDto.setRemainingNumber(remainingNumber);
        registerDto.setPassive(isPassive);
        return registerController.update(id, registerDto);
    }

    // Delete
    private RegisterDto memberDelete() {
        Scanner klavye = new Scanner(System.in);
        RegisterDto registerDto = new RegisterDto();
        Long id;
        System.out.println("Silmek istediğiniz ID'i giriniz");
        id = klavye.nextLong();
        registerDto.setId(id);
        return registerController.deleteById(registerDto);
    }

    // LOGLAMA
    private void logFile() {
        filePathData.logFileReader();
    }


    // Logout
    private void logout() {
        //JOptionPane.
        System.out.println("Sistemden Çıkmak mı istiyor sunuz ? E/H");
        Scanner klavye = new Scanner(System.in);
        char result = klavye.nextLine().toLowerCase().charAt(0);
        if (result == 'e') {
            System.out.println("Sistemden Çıkış yapılıyor iyi günler dileriz.");
            System.exit(0);
        } else {
            System.out.println("Sistemden Çıkış yapılmadı");
        }
    } //end logout()

} //end class
