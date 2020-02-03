package com.example.demo.exception;

public class AgentNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private String msg;

  public AgentNotFoundException(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }
}
