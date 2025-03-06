package com.ap.ai.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.util.List;

public class ResponseModel {
    // ... your ResponseModel code ...
    private int status;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private String message;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private Data data;

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private int id;

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String email;

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        private String phone;

        public String getPhone() {
            return this.phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        private String gender;

        public String getGender() {
            return this.gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        private String DOB;

        public String getDOB() {
            return this.DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        private String network;

        public String getNetwork() {
            return this.network;
        }

        public void setNetwork(String network) {
            this.network = network;
        }

        private String nationality;

        public String getNationality() {
            return this.nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        private String Authorization;

        public String getAuthorization() {
            return this.Authorization;
        }

        public void setAuthorization(String Authorization) {
            this.Authorization = Authorization;
        }

        @SerializedName("interest_tags")
        private List<InterestTag> interestTags;

        public List<InterestTag> getInterestTags() {
            return interestTags;
        }

        public void setInterestTags(List<InterestTag> interestTags) {
            this.interestTags = interestTags;
        }


    }

    public class InterestTag {

        @SerializedName("created_at")
        private String createdAt;
        @Expose
        private Long id;
        @Expose
        private String image;
        @Expose
        private String tags;
        @Expose
        private String title;

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }
} 