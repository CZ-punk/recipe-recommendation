package com.ch.back.ai.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiRequest {


    private List<Content> contents;

    @Data
    public static class Content {

        private Part parts;

    }

    @Data
    public static class Part {

        private String text;

    }

    public AiRequest(String text) {
        this.contents = new ArrayList<>();
        Content content = new Content();
        Part parts = new Part();

        parts.setText(text);
        content.setParts(parts);
        this.contents.add(content);
    }
}
