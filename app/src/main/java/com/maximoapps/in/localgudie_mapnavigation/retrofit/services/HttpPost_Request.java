package com.maximoapps.in.localgudie_mapnavigation.retrofit.services;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${ChandraMohanReddy} on 8/30/2016.
 */
public class HttpPost_Request {
    private static String URL_DEVICE_REGISTRATION = "http://52.33.191.88/ws-listo/device_registration.php";
    private static String URL_USER_REGISTRATIOIN = "http://52.33.191.88/ws-listo/register_user.php";
    private static String URL_USER_LOGIN = "http://52.33.191.88/ws-listo/login.php";
    private static String URL_FORGET_PASSWORD = "http://52.33.191.88/ws-listo/forgot_password.php";
    private static String URL_SEND_LOCATION = "http://52.33.191.88/ws-listo/get_user_location.php";
    private static String URL_CONTACT_UPLOAD = "http://52.33.191.88/ws-listo/test_get_contacts.php";

    private static String URL_CONTACT_CHANGE_LISTNER = "http://52.33.191.88/ws-listo/test_get_contacts.php";


    public static String DeviceRegistration(String encodedString) throws IOException {
        URL url = null;
        try {
            url = new URL(URL_DEVICE_REGISTRATION);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("param", encodedString));

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(getQuery(params));
        writer.flush();
        writer.close();

        os.close();

        conn.connect();


        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            encodedString = response.toString();
            // print result
            System.out.println("RESPONSE STRING " + encodedString);
        } else {
            System.out.println("GET request not worked");
        }
        conn.disconnect();
        return encodedString;
    }

    public static String UserRegistration(String encodedString) throws IOException {
        URL url = null;
        try {
            url = new URL(URL_USER_REGISTRATIOIN);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("param", encodedString));

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(getQuery(params));
        writer.flush();
        writer.close();

        os.close();

        conn.connect();


        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            encodedString = response.toString();
            // print result
            System.out.println("RESPONSE STRING " + encodedString);
        } else {
            System.out.println("GET request not worked");
        }
        conn.disconnect();
        return encodedString;
    }

    public static String UserLogin(String encodedString) throws IOException {
        URL url = null;
        try {
            url = new URL(URL_USER_LOGIN);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("param", encodedString));

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(getQuery(params));
        writer.flush();
        writer.close();

        os.close();

        conn.connect();


        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            encodedString = response.toString();
            // print result
            System.out.println("RESPONSE STRING " + encodedString);
        } else {
            System.out.println("GET request not worked");
        }
        conn.disconnect();
        return encodedString;
    }

    public static String forgetPassword(String encodedString) throws IOException {
        URL url = null;
        try {
            url = new URL(URL_FORGET_PASSWORD);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("param", encodedString));

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(getQuery(params));
        writer.flush();
        writer.close();

        os.close();

        conn.connect();


        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            encodedString = response.toString();
            // print result
            System.out.println("RESPONSE STRING " + encodedString);
        } else {
            System.out.println("GET request not worked");
        }
        conn.disconnect();
        return encodedString;
    }


    private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public static String sendLocation(String encodedString) throws IOException {
        URL url = null;
        try {
            url = new URL(URL_SEND_LOCATION);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("param", encodedString));

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(getQuery(params));
        writer.flush();
        writer.close();

        os.close();

        conn.connect();


        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            encodedString = response.toString();
            // print result
            System.out.println("RESPONSE STRING " + encodedString);
        } else {
            System.out.println("GET request not worked");
        }
        conn.disconnect();
        return encodedString;
    }

    public static String testContactChangeListner() throws IOException {
        URL url = null;
        String encodedString = null;
        try {
            url = new URL(URL_SEND_LOCATION);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.flush();
        writer.close();

        os.close();

        conn.connect();


        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            encodedString = response.toString();
            // print result
            System.out.println("RESPONSE STRING " + encodedString);
        } else {
            System.out.println("GET request not worked");
        }
        conn.disconnect();
        return encodedString;
    }


}