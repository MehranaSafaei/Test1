package com.mehrana.test;

import com.mehrana.test.entity.Personnel;
import com.mehrana.test.service.PersonnelService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        menu();
        try {
            while (running) {
                System.out.print("Please enter an option: ");
                int choice = sc.nextInt();  // Get user input for menu selection
                sc.nextLine();
                switch (choice) {
                    case 1:
                        insert(sc);
                        break;
                    case 2:
                        select();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        System.out.println("Goodbye!");
                        sc.close();
                        System.exit(0);

                        default:
                            System.out.println("Invalid option, please try again... ");
                            break;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }*/
    }

    public static void menu() {
        System.out.println("*** Menu ***");
        System.out.println("1) Add \n 2) Read \n 3) Update \n 4) Remove \n 5) Exit");
    }

    public static Optional<Personnel> insert(Scanner sc) throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        System.out.println("** Enter Information ** \n");
        System.out.print("Enter your username: ");
        String userName = sc.nextLine();
        System.out.print("Enter your mobile number: ");
        String mobile = sc.nextLine();
        System.out.print("Enter your personnel code: ");
        int personnelCode = sc.nextInt();
        sc.nextLine();

        Personnel personnel = new Personnel();
        personnel.setId(personnel.getId());
        personnel.setUserName(userName);
        personnel.setMobile(mobile);
        personnel.setPersonnelCode((long) personnelCode);
        System.out.println("Your information has been saved: " + userName + " - " + mobile + " - " + personnelCode);
        return personnelService.insert(personnel);
    }
    public static List<Personnel> select() throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        List<Personnel> personnelList = personnelService.getListPersonnel();
        System.out.println("    username |  mobile |  personnel code");
        System.out.println("----------------------------------------");
        for (Personnel personnel : personnelList) {
            System.out.println(STR." - \{personnel.getUserName().trim()}    - \{personnel.getMobile()} -\{personnel.getPersonnelCode()}");
        }
        return personnelList;
    }
}