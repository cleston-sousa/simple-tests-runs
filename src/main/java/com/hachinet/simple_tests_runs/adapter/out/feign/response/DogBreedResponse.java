package com.hachinet.simple_tests_runs.adapter.out.feign.response;

import java.util.List;

public class DogBreedResponse {

    private Message message;

    private String status;

    static class Message {

        private List<String> bulldog;

        private List<String> bullterrier;

        private List<String> hound;

        public List<String> getBulldog() {
            return bulldog;
        }

        public void setBulldog(List<String> bulldog) {
            this.bulldog = bulldog;
        }

        public List<String> getBullterrier() {
            return bullterrier;
        }

        public void setBullterrier(List<String> bullterrier) {
            this.bullterrier = bullterrier;
        }

        public List<String> getHound() {
            return hound;
        }

        public void setHound(List<String> hound) {
            this.hound = hound;
        }
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
