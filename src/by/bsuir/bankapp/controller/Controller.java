package by.bsuir.bankapp.controller;

import by.bsuir.bankapp.bean.Client;
import by.bsuir.bankapp.dao.ClientDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@MultipartConfig
public class Controller extends HttpServlet {

    ClientDao clientDao = ClientDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        String command = req.getParameter("command");

        if (command != null) {
            switch (command) {
                case "allClients": {
                    List<Client> clients = clientDao.readAll();
                    req.setAttribute("clients", clients);
                    page = "/index.jsp";
                    break;
                }
                case "getClient": {
                    String passportNumber = (req.getParameter("passportNumber"));
                    Client client = clientDao.readByPassportNumber(passportNumber);
                    req.setAttribute("client", client);
                    page = "/view_client.jsp";
                    break;
                }
                case "openForEdit" : {
                    String passportNumber = (req.getParameter("passportNumber"));
                    Client client = clientDao.readByPassportNumber(passportNumber);
                    req.setAttribute("client", client);
                    page = "/edit_client.jsp";
                    break;
                }
                case "removeClient": {
                    String passportNumber = req.getParameter("passportNumber");
                    clientDao.deleteByPassportNumber(passportNumber);
                    page = "/controller?command=allClients";
                }
            }
        }

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
        requestDispatcher.forward(req, resp);

        //resp.sendRedirect(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        String command = req.getParameter("command");
        boolean isErrorExist = false;

        if (command != null) {
            switch (command) {
                case "createClient": {
                    Client client = new Client();
                    client.setSecName(req.getParameter("firstName"));
                    client.setName(req.getParameter("name"));
                    client.setSurName(req.getParameter("surname"));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                    try {
                        client.setBirthday(new java.sql.Date(dateFormat.parse(req.getParameter("birthDay")).getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (req.getParameter("sexRadio").equals("m")) {
                        client.setSex(true);
                    } else {
                        client.setSex(false);
                    }

                    client.setPassportSeries(req.getParameter("passportSeries"));
                    client.setPassportNumber(req.getParameter("passportNo"));
                    client.setPassportPlace(req.getParameter("passportPlace"));
                    try {
                        client.setPassportDate(new Date(dateFormat.parse(req.getParameter("passportDate")).getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    client.setPassportId(req.getParameter("passportIdNo"));
                    client.setBirthPlace(req.getParameter("birthPlace"));

                    String[] citiesArr = req.getParameter("livingCity").replaceAll(" ", "").split(",");
                    List<String> cities = new ArrayList<>();
                    Collections.addAll(cities, citiesArr);
                    client.setLivingCities(cities);
                    client.setAddress(req.getParameter("address"));
                    client.setHomePhone(req.getParameter("homePhone"));
                    client.setPhone(req.getParameter("phone"));
                    client.setEmail(req.getParameter("email"));

                    List<String> sp = new ArrayList<>();
                    Collections.addAll(sp, req.getParameter("sp").split(","));
                    client.setFamilyMembers(sp);

                    List<String> nationalities = new ArrayList<>();
                    Collections.addAll(nationalities, req.getParameter("nationality").replaceAll(" ", "").split(","));
                    client.setNationalities(nationalities);

                    List<String> ills = new ArrayList<>();
                    Collections.addAll(ills, req.getParameter("ills").split(","));
                    client.setIlls(ills);

                    if (req.getParameterValues("pensioner") == null) {
                        client.setPensioner(false);
                    } else {
                        client.setPensioner(true);
                    }

                    if (req.getParameterValues("military") == null) {
                        client.setMilitary(false);
                    } else {
                        client.setMilitary(true);
                    }

                    client.setIncome(Integer.parseInt(req.getParameter("income")));

                    if (checkByFullName(client.getSecName(), client.getName(), client.getSurName())) {
                        req.getSession().setAttribute("client", client);
                        page = "/controller?command=allClients&message=fullNameMatch";
                    }
                    else if (checkPassportNumber(client.getPassportNumber())) {
                        req.getSession().setAttribute("client", client);
                        page = "/controller?command=allClients&message=passportNoMatch";
                    }
                    else if (checkByPassportId(client.getPassportId())) {
                        req.getSession().setAttribute("client", client);
                        page = "/controller?command=allClients&message=passportIdMatch";
                    }
                    else {
                        req.getSession().removeAttribute("client");
                        clientDao.create(client);
                        page = "/controller?command=allClients";
                    }
                    break;
                }
            }
        }
        resp.sendRedirect(page);
    }

    private boolean checkPassportNumber(String passportNumber) {
        return clientDao.readByPassportNumber(passportNumber) != null;
    }

    private boolean checkByFullName(String secName, String name, String surName) {
        return clientDao.readByFullName(secName, name, surName) != null;
    }

    private boolean checkByPassportId(String passportId){
        return clientDao.readByPassportId(passportId) != null;
    }

    private boolean validateClient(HttpServletRequest req) {
        if (req.getParameter("firstName").isEmpty() || req.getParameter("firstName").length() > 45) return false;
        if (req.getParameter("name").isEmpty() || req.getParameter("name").length() > 45) return false;
        if (req.getParameter("surname").isEmpty() || req.getParameter("surname").length() > 45) return false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        try {
            new java.sql.Date(dateFormat.parse(req.getParameter("birthDay")).getTime());
            new Date(dateFormat.parse(req.getParameter("passportDate")).getTime());
        } catch (ParseException e) {
            return false;
        }
        if (req.getParameter("sexRadio").isEmpty()) return false;
        if (req.getParameter("passportSeries").isEmpty() || req.getParameter("passportSeries").length() > 2)
            return false;
        if (req.getParameter("passportNo").isEmpty() || req.getParameter("passportNo").length() > 45) return false;
        if (req.getParameter("passportPlace").isEmpty() || req.getParameter("passportPlace").length() > 150)
            return false;
        if (req.getParameter("passportIdNo").isEmpty() || req.getParameter("passportIdNo").length() > 45) return false;
        if (req.getParameter("birthPlace").isEmpty() || req.getParameter("birthPlace").length() > 150) return false;
        if (req.getParameter("address").isEmpty() || req.getParameter("address").length() > 150) return false;
        if (req.getParameter("homePhone").length() > 45) return false;
        if (req.getParameter("phone").length() > 45) return false;
        if (req.getParameter("email").length() > 45) return false;
        if (!req.getParameter("income").isEmpty()) {
            try {
                Integer.parseInt(req.getParameter("income"));
            } catch (NumberFormatException e) {
                return false;
            }
        }
        String[] cities = req.getParameter("livingCity").replaceAll(" ", "").split(",");
        if (cities.length < 1) return false;

        if (req.getParameter("sp").split(",").length < 1) return false;

        if (req.getParameter("nationality").replaceAll(" ", "").split(",").length < 1) return false;

        if (req.getParameter("ills").split(",").length < 1) return false;

        return true;
    }
}
