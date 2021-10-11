package com.oneirros.sally2.payload;

import lombok.Data;

@Data
public class AuthRequest {

    private String email;
    private String password;
}
