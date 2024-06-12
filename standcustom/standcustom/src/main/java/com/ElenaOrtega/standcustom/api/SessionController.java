package com.ElenaOrtega.standcustom.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ElenaOrtega.standcustom.bean.UserBean;
import com.ElenaOrtega.standcustom.service.SessionService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/initial/session")
public class SessionController {

    @Autowired
    SessionService oSessionService;

    @PostMapping()
    public ResponseEntity<String> login(@RequestBody UserBean oUserBean) {
        return ResponseEntity.ok("\"" + oSessionService.login(oUserBean) + "\"");
    }

}