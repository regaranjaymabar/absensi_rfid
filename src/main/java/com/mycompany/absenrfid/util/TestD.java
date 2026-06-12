/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absenrfid.util;

/**
 *
 * @author user
 */
public class TestD {
    
    public static void main(String[] args) {
    System.setProperty("KEY", "RahasiaAES123456");
    
    String pwd = EncryptionUtils.encrypt("123");
    System.out.println(pwd);
    }
    
}
