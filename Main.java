import java.util.*;
import java.sql.*;

public class Main {

  // #############################----checkRoomAvailable---#######################################
  public static int checkRoomAvailable() throws Exception {
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery("select  count(*) from details where tag is null");
    rs.next();
    int count = rs.getInt(1);
    if (count > 0) {
      System.out.println(" rooms are available");
      return count;
    } else {
      System.out.println(" SORRY!  rooms are full ");
      return count;
    }
  }
  // ########################----book
  // Room----############################################

  public static void bookRoom() throws Exception {
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery("select * from details where tag is null;");
    rs.next();
    int idno = rs.getInt(1);
    System.out.println(" id no " + idno);
    Scanner sc = new Scanner(System.in);
    System.out.println(" Enter your name");
    String name = sc.nextLine();
    System.out.println("Enter your phone number");
    String phnumber = sc.nextLine();
    System.out.println("NUMBER OF DAYS TO STAY");
    int staydays = sc.nextInt();
    PreparedStatement ps = con.prepareStatement(
        "update details set name=?,roomno=id+100,phoneno=?,ardate=NOW(),dpdate=DATE_ADD(NOW(), INTERVAL ? DAY),tag=10+id where id="
            + idno + ";");
    ps.setInt(3, staydays);
    ps.setString(1, name);
    ps.setString(2, phnumber);
    int affected = ps.executeUpdate();
    System.out.println(" thank you " + affected + " room is booked");

  }

  // #########################--check status--######################//#endregion
  public static void checkStatus() throws Exception {
    Scanner sc = new Scanner(System.in);
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
    System.out.println("Enter the id no to check your details");
    int idno = sc.nextInt();
    if (idno < 1 || idno > 9) {
      System.out.println("no such room");
    } else {
      Statement st = con.createStatement();
      Statement st2 = con.createStatement();
      ResultSet rs = st.executeQuery("select * from details where id=" + idno + " ; ");
      rs.next();
      ResultSet rs2 = st2.executeQuery("select DATEDIFF(dpdate,NOW()) as remainingday from details;");
      rs2.next();
      System.out.println("room no " + rs.getInt(3) + "\nName : " + rs.getString(2) + " \n Phone number :"
          + rs.getString(4) + " \n number of days remaining :" + rs2.getInt(1));

    }
  }

  // ###################----checkOut---#################################//#endregion
  public static void checkOut() throws Exception {
    Scanner sc = new Scanner(System.in);
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
    boolean confirm = true;
    System.out.println(" Are you sure want to check out ?");
    System.out.println("type YES to proceed further");
    System.out.println(" Type no to cancel");
    String str = sc.nextLine();
    if (str.equalsIgnoreCase(("yes").trim())) {
      System.out.println(" enter your room number :");
      int roomno = sc.nextInt();
      if (roomno > 100 && roomno < 110) {
        PreparedStatement ps = con
            .prepareStatement("update details set name='',tag=null,dpdate=null,ardate=null,phoneno='' where roomno=?;");
        ps.setInt(1, roomno);
        int aff = ps.executeUpdate();
        System.out.println(aff + "room is checked out");
      } else {
        System.out.println(" no such room number");
      }
    } else {
      System.out.println(" Thank you");
    }
    System.out.println(" thank you updated");

  }

  // ##########################----MAIN METHOD----################################
  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    System.out.println(
        " Welcome to \" ROYAL PALACE\"  ,Book your rooms here  ");
    Boolean b = true;
    while (b == true) {
      System.out.println("type the number which you want to perform");
      System.out.println("type 1 to Book rooms");
      System.out.println("type 2 to check the status");
      System.out.println("type 3 to checkOut the room");
      System.out.println("|type 4 to exit|");
      int num = sc.nextInt();
      switch (num) {
        case 1: {
          checkRoomAvailable();
          if (checkRoomAvailable() > 0) {
            bookRoom();
            System.out.println("\n\n\n\n\n\n\n\n");
          }
          break;
        }
        case 2: {
          checkStatus();
          System.out.println("\n\n\n\n\n\n\n\n");
          break;
        }
        case 3: {
          checkOut();
          System.out.println("\n\n\n\n\n\n\n\n");
          break;
        }
        case 4: {
          System.out.println(" thank you come again");
          System.out.println("\n\n\n\n\n\n\n\n");
          b = false;
          break;
        }
        default: {
          System.out.println(" check the number");
          System.out.println("\n\n\n\n\n\n\n\n");
        }
      }
    }
  }
}
