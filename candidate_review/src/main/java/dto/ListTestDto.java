package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.Questions;
import model.Users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lubomir on 11/9/2015.
 */
public class ListTestDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("info")
    private String info;

    @JsonProperty("name")
    private String name;

    @JsonProperty("position")
    private String position;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
