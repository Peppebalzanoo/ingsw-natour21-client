package com.example.natour2.model;

public class Email {
    private String email;
    private boolean checked;

    public Email(String email){
        this.email = email;
        this.checked = false;
    }

    public void setChecked(){
        this.checked = true;
    }
    public void setUnChecked(){
        this.checked = false;
    }
    public boolean getCheckStatus(){
        return this.checked;
    }
    public String getEmail(){
        return this.email;
    }

}
