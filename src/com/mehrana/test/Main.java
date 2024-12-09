package com.mehrana.test;

import com.mehrana.test.entity.Leave;
import com.mehrana.test.entity.Personnel;
import com.mehrana.test.service.LeaveService;
import com.mehrana.test.service.PersonnelService;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

//        String x = "hey";
//        x = "hoy";
//        System.out.println(x);

        String[] str = new String[]{"mehrana", "mehraneh"};
        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]);
        }
        menu();
        try {
            while (running) {
                System.out.print("Please enter an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline left by nextInt()

                switch (choice) {
                    case 1:
                        insert(sc); //this method for adding information
                        break;
                    case 2:
                        select(); //this method for selecting
                        break;
                    case 3:
                        update(sc); //this method for updating
                        break;
                    case 4:
                        remove(sc); //this method fo removing
                        break;
                    case 5:
                        searchByName(sc); // New method for searching by username
                        break;
                    case 6:
                        insertLeave(sc);
                        break;
                    case 7:
                        updateLeave(sc);
                        break;
                    case 8:
                        System.out.println("Goodbye!");
                        sc.close();
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option, please try again...");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void menu() {
        System.out.println("*** Menu ***");
        System.out.println("1) Add");
        System.out.println("2) Read");
        System.out.println("3) Update");
        System.out.println("4) Remove");
        System.out.println("5) Search by Name");
        System.out.println("6)  insert Leave");
        System.out.println("7) update Leave");
        System.out.println("8) Exit");
    }

    public static Optional<Personnel> insert(Scanner sc) throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        System.out.println("** Enter Information **");
        System.out.print("Enter your username: ");
        String userName = sc.nextLine();
        System.out.print("Enter your mobile number: ");
        String mobile = sc.nextLine();
        System.out.print("Enter your personnel code: ");
        int personnelCode = sc.nextInt();
        sc.nextLine();

        Personnel personnel = new Personnel();
        personnel.setUserName(userName);
        personnel.setMobile(mobile);
        personnel.setPersonnelCode((long) personnelCode);
        System.out.println("Your information has been saved: " + userName + " - " + mobile + " - " + personnelCode);
        return personnelService.insert(personnel);
    }

    public static List<Personnel> select() throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        List<Personnel> personnelList = personnelService.getListPersonnel();
        System.out.println("    Username | Mobile | Personnel Code");
        System.out.println("--------------------------------------");
        for (Personnel personnel : personnelList) {
            System.out.println(personnel.getUserName().trim() + " - " + personnel.getMobile() + " - " + personnel.getPersonnelCode());
        }
        return personnelList;
    }

    public static Personnel update(Scanner sc) throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        System.out.print("Enter your personnel code: ");
        long personnelCode = sc.nextLong();
        sc.nextLine();

        Personnel existingPersonnel = personnelService.findPersonnelByCode(personnelCode);
        if (existingPersonnel == null) {
            System.out.println("Personnel not found!");
            return null;
        }

        System.out.println("** Enter New Information **");
        System.out.print("Enter your username: ");
        String userName = sc.nextLine();
        System.out.print("Enter your mobile number: ");
        String mobile = sc.nextLine();
        existingPersonnel.setUserName(userName);
        existingPersonnel.setMobile(mobile);

        Personnel updatedPersonnel = personnelService.updatePersonnel(existingPersonnel);
        if (updatedPersonnel != null) {
            System.out.println("Personnel updated successfully: " + updatedPersonnel);
        } else {
            System.out.println("Failed to update personnel.");
        }

        return updatedPersonnel;
    }

    public static void remove(Scanner sc) throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        System.out.print("Enter your ID: ");
        long id = sc.nextLong();
        sc.nextLine();

        Optional<Personnel> existingPersonnel = personnelService.findById(id);

        if (existingPersonnel.isPresent()) {
            personnelService.deleteById(id);
            System.out.println("Personnel with ID " + id + " has been deleted successfully.");
        } else {
            System.out.println("Personnel not found with ID " + id + "!");
        }
    }

    //  Search by Name
    public static void searchByName(Scanner sc) throws SQLException {
        PersonnelService personnelService = new PersonnelService();
        System.out.print("Enter the username to search: ");
        String name = sc.nextLine();

        List<Personnel> results = personnelService.findPersonnelByName(name);
        if (results.isEmpty()) {
            System.out.println("No personnel found with the name: " + name);
        } else {
            System.out.println("    Username | Mobile | Personnel Code");
            System.out.println("--------------------------------------");
            for (Personnel personnel : results) {
                System.out.println(personnel.getUserName().trim() + " - " + personnel.getMobile() + " - " + personnel.getPersonnelCode());
            }
        }
    }

    public static Optional<Leave> insertLeave(Scanner sc) throws SQLException, ParseException {
        System.out.print("enter your personnelCode: ");
        int personnelCode = sc.nextInt();

        PersonnelService personnelService = new PersonnelService();
        Personnel p = personnelService.findPersonnelByCode(personnelCode);

        if (p == null) {
            System.out.println("Personnel not found.");
            return Optional.empty();
        }

        LeaveService leaveService = new LeaveService();
        Scanner scanner = new Scanner(System.in);
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Calendar.getInstance().getTime();
        String todayAsString = simpleDateFormat.format(today);
        System.out.println("Today's date: " + todayAsString);
        System.out.print("Enter your starting date(\"YYYY-MM-DD\"): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter your ending date(\"YYYY-MM-DD\"): ");
        String endDate = scanner.nextLine();
        System.out.print("Enter your description: ");
        String description = scanner.nextLine();


        Leave leave = new Leave();
        leave.setStartDate(simpleDateFormat.parse(startDate));
        leave.setEndDate(simpleDateFormat.parse(endDate));
        leave.setDescription(description);
        leave.setPersonnelId(p.getId()); //Setting the employee ID to leave
        System.out.println("Your information has been saved: " + startDate + " - " + endDate + " - " + description);
        return leaveService.insert(leave);
    }


    public static Leave updateLeave(Scanner sc) throws SQLException, ParseException {
   /*     LeaveService leaveService = new LeaveService();
        PersonnelService personnelService = new PersonnelService();

        System.out.print("Enter your personnel code: ");
        long personnelCode = sc.nextLong();
        sc.nextLine();

        Personnel personnel = personnelService.findPersonnelByCode(personnelCode);
        if (personnel == null) {
            System.out.println("Personnel not found!");
            return null;
        }

        Optional<Leave> existingLeave = leaveService.findLeaveByPersonnelId(personnel.getPersonnelCode());
        if (existingLeave == null) {
            System.out.println("No leave record found for this personnel.");
            return Optional.empty();
        }

        System.out.println("** Enter New Leave Information **");
        System.out.print("Enter new description: ");
        String description = sc.nextLine();
        System.out.print("Enter new leave start date (yyyy-MM-dd): ");
        String startDateStr = sc.nextLine();
        System.out.print("Enter new leave end date (yyyy-MM-dd): ");
        String endDateStr = sc.nextLine();

        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);

        existingLeave.get().setDescription(description);
        existingLeave.get().setStartDate(startDate);
        existingLeave.get().setEndDate(endDate);

        Optional<Object> updatedLeave = leaveService.update(existingLeave);
        if (updatedLeave == null) {
            System.out.println("Leave updated successfully: " + updatedLeave);
        } else {
            System.out.println("Failed to update leave.");
        }

        return Optional.of(updatedLeave);*/
        System.out.print("Enter your ID: ");


    }


}

