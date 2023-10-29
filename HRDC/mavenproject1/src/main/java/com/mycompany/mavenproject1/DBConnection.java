/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.sql.*;
import java.util.*;


public class DBConnection {
 String url = "jdbc:mysql://127.0.0.1:3307/HRDC";
 String username = "root";
 String password = "";

 
 public void insertData(String user, String pass, String name, int age, String TxtDateString, String TxtMobile, String TxtEmail, String TxtFather, String TxtMother, String TxtOccupation ,String TxtLocality,String TxtCity,String TxtState,String TxtCountry,int TxtPincode,String TxtHS,String TxtInter,String TxtGrad,int TxtCGPAHS,int TxtCGPAInter,int TxtCGPAGrad) throws SQLException{
     try (Connection conn = DriverManager.getConnection(url, username, password)) {
         PreparedStatement pstmt = conn.prepareStatement("INSERT INTO personaldetail(username,password,name,age,dob,mobile,email,Fathername,Mothername,Fatheroccupation,isHired,Locality,City,State,Country,Pincode,HighschoolCLG,InterCLG,GraduationCLG,HighschoolCGPA,InterCGPA,GraduationCGPA) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
         pstmt.setString(1, user);
         pstmt.setString(2, pass);
         pstmt.setString(3, name);
         pstmt.setInt(4, age);
         pstmt.setString(5, TxtDateString);
         pstmt.setString(6, TxtMobile);
         pstmt.setString(7, TxtEmail);
         pstmt.setString(8, TxtFather);
         pstmt.setString(9, TxtMother);
         pstmt.setString(10, TxtOccupation);
         pstmt.setString(11, "n");
         pstmt.setString(12, TxtLocality);
         pstmt.setString(13, TxtCity);
         pstmt.setString(14, TxtState);
         pstmt.setString(15, TxtCountry);
         pstmt.setInt(16, TxtPincode);
         pstmt.setString(17, TxtHS);
         pstmt.setString(18, TxtInter);
         pstmt.setString(19, TxtGrad);
         pstmt.setInt(20, TxtCGPAHS);
         pstmt.setInt(21, TxtCGPAInter);
         pstmt.setInt(22, TxtCGPAGrad);
         
         
         pstmt.executeUpdate();
         pstmt.close();
         conn.close();
     }
 }
 public void insertAppraisal(String user, String work, String learning, String improvement, String self) throws SQLException{
     try (Connection conn = DriverManager.getConnection(url, username, password)) {
         PreparedStatement pstmt = conn.prepareStatement("INSERT INTO appraisal(Username,Work,Learning,improvement,SelfReview,reviewed) VALUES(?,?,?,?,?,?)");
         pstmt.setString(1, user);
         pstmt.setString(2, work);
         pstmt.setString(3, learning);
         pstmt.setString(4, improvement);
         pstmt.setString(5, self);
         pstmt.setString(6, "n");
         pstmt.executeUpdate();
         pstmt.close();
         conn.close();
     }
 }
  public boolean insertResignation(String user, String reason) throws SQLException{
     try (Connection conn = DriverManager.getConnection(url, username, password)) {
         boolean check = true;
         PreparedStatement pstmt1 = conn.prepareStatement("SELECT * FROM resignation WHERE Username = ? ");
         pstmt1.setString(1, user);
         ResultSet rs = pstmt1.executeQuery();
         while(rs.next()) {
             check = false;
         }
         if(check){
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO resignation(Username,reason,status) VALUES(?,?,?)");
            pstmt.setString(1, user);
            pstmt.setString(2, reason);
            pstmt.setString(3, "PENDING");
            pstmt.executeUpdate();
         }
         conn.close();
         return check;
     }
 }
 public String getName(String col,String user) throws SQLException {
     String res;
     try (Connection conn = DriverManager.getConnection(url, username, password)) {
         res = "";
         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM personaldetail WHERE username = ? ");
         pstmt.setString(1, user);
         ResultSet rs = pstmt.executeQuery();
         while(rs.next()) {
             res = rs.getString(col);
         }
     }
	 return res;
 }
 public void applicationStatus(String status, String user) throws SQLException{
     try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE personaldetail SET isHired = ? where username = ? ");
            pstmt.setString(1, status);
            pstmt.setString(2, user);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
     }
 }
 
 public void makeOffer(String user, String offer, String cmnt) throws SQLException{
     try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE resignation SET offer = ?, comment = ? ,status = 'OFFERED' where Username = ? ");
            pstmt.setString(1, offer);
            pstmt.setString(2, cmnt);
            pstmt.setString(3, user);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
     }
 }
 
 public void reviewAppraisal(String comment, int rating, String user) throws SQLException{
      try (Connection conn = DriverManager.getConnection(url, username, password)){ 
        PreparedStatement pstmt = conn.prepareStatement("UPDATE appraisal SET Comment = ? , Rating = ?, reviewed= 'y' where username = ? ");
        pstmt.setString(1, comment);
        pstmt.setInt(2, rating);
        pstmt.setString(3, user);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
      }
 }
 public boolean checkAppraisalPresence(String user) throws SQLException {
     String res;
     try (Connection conn = DriverManager.getConnection(url, username, password)) {
         res = "";
         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM appraisal WHERE username = ? ");
         pstmt.setString(1, user);
         ResultSet rs = pstmt.executeQuery();
         while(rs.next()) {
             return false;
         }
     }
	 return true;
 }
 
 
    public void acceptHROffer(String user) throws SQLException{
       try (Connection conn = DriverManager.getConnection(url, username, password)) {
           PreparedStatement pstmt = conn.prepareStatement("DELETE FROM resignation WHERE Username = ? ");
           pstmt.setString(1, user);
           pstmt.executeUpdate();
           pstmt.close();
           conn.close();
       }
   }   
    
   public void rejectHROffer(String user) throws SQLException{
       try (Connection conn = DriverManager.getConnection(url, username, password)) {
           PreparedStatement pstmt = conn.prepareStatement("DELETE FROM personaldetail WHERE Username = ? ");
           pstmt.setString(1, user);
           pstmt.executeUpdate();
           pstmt.close();
           conn.close();
       }
   } 
   public void acceptLeave(String id) throws SQLException{
       try (Connection conn = DriverManager.getConnection(url, username, password)) {
           PreparedStatement pstmt = conn.prepareStatement("Update applyleave set Status = 'APPROVED' WHERE ID = ? ");
           pstmt.setString(1, id);
           pstmt.executeUpdate();
           pstmt.close();
           conn.close();
       }
   } 
   public void rejectLeave(String id) throws SQLException{
       try (Connection conn = DriverManager.getConnection(url, username, password)) {
           PreparedStatement pstmt = conn.prepareStatement("Update applyleave set Status = 'REJECTED' WHERE ID = ? ");
           pstmt.setString(1, id);
           pstmt.executeUpdate();
           pstmt.close();
           conn.close();
       }
   } 
}
