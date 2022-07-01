package com.c1se44.school_connect.API.admin;


import com.c1se44.school_connect.DTO.Request.EmailRequest;
import com.c1se44.school_connect.DTO.Request.ViewRequest;
import com.c1se44.school_connect.DTO.Request.userRequest;
import com.c1se44.school_connect.DTO.Response.MessageResponse;
import com.c1se44.school_connect.DTO.Response.baseResponse;
import com.c1se44.school_connect.DTO.Response.userResponse;
import com.c1se44.school_connect.DTO.jwt.JwtResponse;
import com.c1se44.school_connect.entity.RoleName;
import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.roleEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.security.JWT.JwtProvider;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.service.IForumService;
import com.c1se44.school_connect.service.impl.RoleService;
import com.c1se44.school_connect.service.impl.UserService;
import com.c1se44.school_connect.service.impl.emailService;
import com.c1se44.school_connect.utils.CheckRole;
import com.c1se44.school_connect.utils.RandomString;
import com.sendgrid.Response;
import io.swagger.annotations.Api;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin(origins = "*")
@RequestMapping("/api/admin/account")
@RestController()
@Api(value = "account user manager")
public class AccountControllerAPI {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JwtTokenFilter jwtTokenFilter;
    @Autowired
    CheckRole checkRole;
    @Autowired
    emailService EmailService;
    @Autowired
    IForumService forumService;
    
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
    private static Pattern pattern;
    
    @GetMapping("/view")
    public ResponseEntity<?> viewAllUsers(HttpServletRequest request,
                                          @RequestParam(value = "page", required = true) int page,
                                          @RequestParam(value = "sortBy", required = true) String sortBy) {
        String jwt = jwtTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromToken(jwt);
        if (!checkRole.isAdmin(username)) {
            return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
        }
        try {
            ViewRequest view = new ViewRequest();
            Pageable pageable = PageRequest.of(page - 1, view.getLimitAdmin(), Sort.by(sortBy).descending());
            List<userEntity> users = userService.findAll(StatusName.is_active, pageable);
            List<userResponse> userResponses = new ArrayList<>();
            for (userEntity userEntity : users) {
                userResponse userResponse = new userResponse(userEntity);
                List<String> roleResponse =new ArrayList<>();
                userEntity.getRoles().forEach(item ->{
                    roleResponse.add(item.getCode().toString());
                });
                userResponse.setRoles(roleResponse);
                userResponses.add(userResponse);
            }
            int totalPage = (int) Math.ceil((double) (userService.countAllUserIdByStatus(StatusName.is_active)) / view.getLimitAdmin());
            baseResponse Response = new baseResponse();
            Response.setListResult(Arrays.asList(userResponses.toArray()));
            Response.setTotalPage(totalPage);
            return new ResponseEntity<>(Response, HttpStatus.OK);
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewAccountUsers(HttpServletRequest request, @PathVariable("id") Long id) {
        String jwt = jwtTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromToken(jwt);
        if (!checkRole.isAdmin(username)) {
            return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
        }
        userEntity user;
        try {
            roleEntity centerRole = roleService.findByCode(RoleName.CENSOR).orElseThrow(() -> new RuntimeException("Role CENSOR NOT FOUND"));
            roleEntity centerAdmin = roleService.findByCode(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role CENSOR NOT FOUND"));
            
            user = userService.findByUserId(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> userid" + id));
            
            userResponse userResponse = new userResponse(user);
            if (userService.existsByRolesAndUserid(centerRole, id)) {
                userResponse.setRoleOfUser("CENSOR");
            } else if (userService.existsByRolesAndUserid(centerAdmin, id)) {
                userResponse.setRoleOfUser("ADMIN");
            } else {
                userResponse.setRoleOfUser("USER");
            }
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/grantPermission")
    public Set<roleEntity> grantPermission(List<String> strRoles) {
        Set<roleEntity> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "ADMIN":
                    roleEntity adminRole = roleService.findByCode(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role ADMIN NOT FOUND"));
                    roles.add(adminRole);
                    roleEntity userscencerRole = roleService.findByCode(RoleName.USER).orElseThrow(() -> new RuntimeException("ROLE use NOT FOUND"));
                    roles.add(userscencerRole);
                    break;
                case "CENSOR":
                    roleEntity cencerRole = roleService.findByCode(RoleName.CENSOR).orElseThrow(() -> new RuntimeException("Role CENSOR NOT FOUND"));
                    roles.add(cencerRole);
                    roleEntity usercencerRole = roleService.findByCode(RoleName.USER).orElseThrow(() -> new RuntimeException("ROLE use NOT FOUND"));
                    roles.add(usercencerRole);
                    break;
                case "USER":
                    roleEntity userRole = roleService.findByCode(RoleName.USER).orElseThrow(() -> new RuntimeException("ROLE use NOT FOUND"));
                    roles.add(userRole);
                    break;
            }
        });
        return roles;
    }
    
    @GetMapping("/search/{key}")
    public ResponseEntity<?> searchUser(HttpServletRequest request, @PathVariable("key") String key,
                                        @RequestParam(value = "page", required = true) int page,
                                        @RequestParam(value = "sortBy", required = true) String sortBy) {
        String jwt = jwtTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromToken(jwt);
        try {
            ViewRequest ViewRequest = new ViewRequest();
            Pageable pageable = PageRequest.of(page - 1, ViewRequest.getLimitAdmin(), Sort.by(sortBy).ascending());
            List<userEntity> users;
//            if (Maso != null)
//                users = userService.findAllByStatusAndMaso(1, Maso, pageable);
            if (key != null)
                users = userService.findAllByStatusAndFullName(StatusName.is_active, key, pageable);
            else
                return viewAllUsers(request,page,sortBy);
            List<userResponse> userResponses = new ArrayList<>();
            for (userEntity userEntity : users) {
                userResponse userResponse = new userResponse(userEntity);
                List<String> roleResponse =new ArrayList<>();
                userEntity.getRoles().forEach(item ->{
                    roleResponse.add(item.getCode().toString());
                });
                userResponse.setRoles(roleResponse);
                userResponses.add(userResponse);
            }
            int totalPage = (int) Math.ceil((double) (userService.countAllByStatusAndUsernameContaining(StatusName.is_active,key) / ViewRequest.getLimitAdmin()));
            baseResponse Response = new baseResponse();
            Response.setListResult(Arrays.asList(userResponses.toArray()));
            Response.setTotalPage(totalPage);
            return new ResponseEntity<>(Response, HttpStatus.OK);
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>(new JwtResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    
    
    
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(HttpServletRequest request, @Valid @RequestBody userRequest userRequest) {
        String jwt = jwtTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromToken(jwt);
        if (!checkRole.isAdmin(username)) {
            return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
        }
        if (userService.existsByUsernameAndStatus(userRequest.getUserName(), StatusName.is_active)) {
            return new ResponseEntity<>(new MessageResponse("user exists"), HttpStatus.OK);
        }
        if (userService.existsByEmailAndStatus(userRequest.getEmail(), StatusName.is_active)) {
            return new ResponseEntity<>(new MessageResponse("email exists"), HttpStatus.OK);
        }
        if (userService.existsByMasoAndStatus(userRequest.getCode(), StatusName.is_active)) {
            return new ResponseEntity<>(new MessageResponse("Code exists"), HttpStatus.OK);
        }
        if (userRequest.getAvatar() == null || userRequest.getAvatar().trim().isEmpty()) {
            userRequest.setAvatar("https://firebasestorage.googleapis.com/v0/b/school-conection.appspot.com/o/avatar_mac_dinh.png?alt=media&token=cffd2f64-f96a-453c-848e-a7a6d042ea85");
        }
        int numberOfCharactor = 8;
        RandomString rand = new RandomString();
        String passwordNew = rand.randomAlphaNumeric(numberOfCharactor);
        userEntity user = new userEntity(userRequest, passwordEncoder.encode(passwordNew));
        Set<roleEntity> roles = grantPermission(userRequest.getRoles());
        user.setRoles(roles);
        userService.save(user);
        String status = notifyMember(user, passwordNew, "create");
        return new ResponseEntity<>(new MessageResponse("create success!!! email " + status), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteUser(HttpServletRequest request, @PathVariable("id") Long id) {
        String jwt = jwtTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromToken(jwt);
        if (!checkRole.isAdmin(username)) {
            return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
        }
    
        userEntity Censor = userService.findByUserId(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        if (forumService.existsByCensorId(StatusName.is_active, Censor)) {
            return new ResponseEntity<>(new MessageResponse("user is the censor is working"), HttpStatus.OK);
        }
        AtomicInteger checkAdmin= new AtomicInteger();
        Censor.getRoles().forEach(item -> {
            if(item.getCode().equals(RoleName.ADMIN)){
                checkAdmin.set(1);
            }
        });
        if(checkAdmin.get()==1){
            return new ResponseEntity<>(new MessageResponse("user is the admin cannot delete"), HttpStatus.OK);
        }
        String status = notifyMember(Censor, null, "delete");
        userService.updateStatusById(StatusName.stop_working, id);
        return new ResponseEntity<>(new MessageResponse("yes"), HttpStatus.OK);
    
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(HttpServletRequest request, @Valid @RequestBody userRequest userRequest) {
        String jwt = jwtTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromToken(jwt);
        if (!checkRole.isAdmin(username)) {
            return new ResponseEntity<>(new MessageResponse("not admin"), HttpStatus.OK);
        }
        userEntity usercurrent;
        try {
            usercurrent = userService.findByUserId(userRequest.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User Not Found wiht -> username" + username));
            if (!usercurrent.getUsername().equals(userRequest.getUserName()) && userService.existsByUsernameAndStatus(userRequest.getUserName(), StatusName.is_active)) {
                return new ResponseEntity<>(new MessageResponse("user exists"), HttpStatus.OK);
            }
            if (!usercurrent.getEmail().equals(userRequest.getEmail()) && userService.existsByEmailAndStatus(userRequest.getEmail(), StatusName.is_active)) {
                return new ResponseEntity<>(new MessageResponse("email exists"), HttpStatus.OK);
            }
            if (!usercurrent.getCode().equals(userRequest.getCode()) && userService.existsByMasoAndStatus(userRequest.getCode(), StatusName.is_active)) {
                return new ResponseEntity<>(new MessageResponse("code exists"), HttpStatus.OK);
            }
            if (userRequest.getAvatar() == null || userRequest.getAvatar().trim().isEmpty()) {
                userRequest.setAvatar("https://firebasestorage.googleapis.com/v0/b/school-conection.appspot.com/o/avatar_mac_dinh.png?alt=media&token=cffd2f64-f96a-453c-848e-a7a6d042ea85");
            }
            userEntity userChange = new userEntity(userRequest);
            userChange.setCreateDate(usercurrent.getCreateDate());
            userChange.setCreatedby(usercurrent.getCreatedby());
            userChange.setPassword(usercurrent.getPassword());
            Set<roleEntity> roles = grantPermission(userRequest.getRoles());
            userChange.setRoles(roles);
            userService.save(userChange);
            String status = notifyMember(userChange, null, "update");
            return new ResponseEntity<>(new MessageResponse("update success!!! email: " + status), HttpStatus.OK);
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    
    public String notifyMember(userEntity member, String password, String acction) {
        String message;
        if (acction.equals("create")) {
            message = "Admin created an account for you" +
                        "\nYour account registration information has been approved successfully!" +
                        "\nUsername:" + member.getUsername() +
                        "\nPassword:" + password;
            EmailRequest email = new EmailRequest(member.getEmail(), "Create_account", message);
            Response response = EmailService.sendemail(email);
            if (response.getStatusCode() == 200 || response.getStatusCode() == 202) {
                return "sent success";
            }
        } else if (acction.equals("update")) {
            message = "Admin update an account for you" +
                        "\nYour account update information has been approved successfully!" +
                        "\nUsername:" + member.getUsername() +
                        "\nEmail:" + member.getEmail();
            EmailRequest email = new EmailRequest(member.getEmail(), "Update_account", message);
            Response response = EmailService.sendemail(email);
            if (response.getStatusCode() == 200 || response.getStatusCode() == 202) {
                return "sent success";
            }
        } else if (acction.equals("delete")) {
            message = "Admin update an account for you" +
                        "\nYour account was delete by admin";
            EmailRequest email = new EmailRequest(member.getEmail(), "delete_account", message);
            Response response = EmailService.sendemail(email);
            if (response.getStatusCode() == 200 || response.getStatusCode() == 202) {
                return "sent success";
            }
        }
        System.out.println(password);
        return "not ok";
    }
    
    //TODO:fix this
    @PostMapping(value="/importOrderExcel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importExcelFile(@RequestParam("file") MultipartFile files) throws IOException {
        List<userRequest> userRequestList = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
        // Read student data form excel file sheet1.
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);
                userRequest userRequest = new userRequest();
                if (getCellValue(row, 0) != null
                            && isNumeric(getCellValue(row, 0))
                            && getCellValue(row, 1).length() >= 6
                            && isDateValid(getCellValue(row, 7))
                            && getCellValue(row, 6).length() ==10
                            && getCellValue(row, 1) != null
                            && validateString(getCellValue(row, 1))
                            && getCellValue(row, 9) != null
                            && getCellValue(row, 3) != null
                            && validate(getCellValue(row, 2))
                            && getCellValue(row, 0).length() >= 4
                            && validateStringPosition(getCellValue(row, 8))
                            && validateStringRole(getCellValue(row, 9))
                            && validateStringGender(getCellValue(row,4))){
                    userRequest.setCode(convertStringToLong(getCellValue(row, 0)));
                    userRequest.setUserName(getCellValue(row, 1));
                    userRequest.setEmail(getCellValue(row, 2));
                    userRequest.setFullName(getCellValue(row, 3));
                    userRequest.setGender(getCellValue(row, 4));
                    userRequest.setAddressOfUser(getCellValue(row, 5));
                    userRequest.setNumberPhone(getCellValue(row, 6));
                    userRequest.setDateOfBirth(convertStringToDate(getCellValue(row, 7)));
                    userRequest.setPosition(getCellValue(row, 8));
                    List<String> role = new ArrayList<>();
                    role.add(getCellValue(row, 9));
                    userRequest.setRoles(role);
                    userRequestList.add(userRequest);
                }
            }
        }
        List<userRequest> userResponseList = new ArrayList<>();
        userRequestList.forEach(userRequest -> {
            if (!userService.existsByUsernameAndStatus(userRequest.getUserName(), StatusName.is_active)) {
                if (!userService.existsByEmailAndStatus(userRequest.getEmail(), StatusName.is_active)) {
                    if (!userService.existsByMasoAndStatus(userRequest.getCode(), StatusName.is_active)) {
                        if (userRequest.getAvatar() == null || userRequest.getAvatar().trim().isEmpty()) {
                            userRequest.setAvatar("https://firebasestorage.googleapis.com/v0/b/school-conection.appspot.com/o/avatar_mac_dinh.png?alt=media&token=cffd2f64-f96a-453c-848e-a7a6d042ea85");
                        }
                        int numberOfCharactor = 8;
                        RandomString rand = new RandomString();
                        String passwordNew = rand.randomAlphaNumeric(numberOfCharactor);
                        userEntity user = new userEntity(userRequest, passwordEncoder.encode(passwordNew));
                        Set<roleEntity> roles = grantPermission(userRequest.getRoles());
                        user.setRoles(roles);
                        userService.save(user);
                        String status = notifyMember(user, passwordNew, "create");
                        userResponseList.add(userRequest);
                    }
                }
            }
        });
        return new ResponseEntity<>(userResponseList, HttpStatus.OK);
    }
    private Matcher matcher;
    
    public static Boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
    
    public static Boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    // Class check format email
    public Boolean validate(final String hex) {
        pattern = Pattern.compile(EMAIL_REGEX);
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }
    
    // Class check format email
    public Boolean validateString(final String hex) {
        pattern = Pattern.compile("^[a-zA-Z0-9]*$");
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }
    
    public Boolean validateStringPosition(final String hex) {
        if (hex.equals("Student") || hex.equals("Teacher")||hex.equals("student") || hex.equals("teacher"))
            return true;
        else
            return false;
    }
    public Boolean validateStringGender(final String hex) {
        if (hex.equals("nam") || hex.equals("nu"))
            return true;
        else
            return false;
    }
    public Boolean validateStringRole(final String hex) {
        if (hex.equals("USER") || hex.equals("ADMIN") || hex.equals("CENSOR"))
            return true;
        else
            return false;
    }
    
    private Long convertStringToLong(String str) {
        Long result = 0L;
        if (str == null || str.isEmpty() || str.trim().isEmpty()) {
            return result;
        }
        result = Long.valueOf((str));
        return result;
    }
    
    private LocalDate convertStringToDate(String str) {
        LocalDate result = null;
        if (str == null || str.isEmpty() || str.trim().isEmpty()) {
            return result;
        }
        result = LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE);
        return result;
    }
    
    private String getCellValue(Row row, int cellNo) {
        DataFormatter formatter = new DataFormatter();
        Cell cell = row.getCell(cellNo);
        return formatter.formatCellValue(cell);
    }
    
}
