package com.lix.pushmessage.controller;


import com.lix.pushmessage.domain.NewMessage;
import com.lix.pushmessage.domain.WarningMessage;
import com.lix.pushmessage.service.ProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ProController {
    @Autowired
    ProService proService;

    @RequestMapping("/sendNewMessage")
    public String send(@RequestBody @Valid NewMessage message){
        return proService.sendNewMessage(message);
    }
    @RequestMapping("/sendWarningMessage")
    public String send(@RequestBody @Valid WarningMessage message){
        return proService.sendWarningMessage(message);
    }
}
