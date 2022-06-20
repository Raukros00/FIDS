package com.fids.Centrale;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AggiornaInventarioControl {
    private int day;
    private int month;
    private int year;



    public void aggiornaInventario() {
        Timeline clock = new Timeline(new KeyFrame(Duration.millis(1000 - Calendar.getInstance().get(Calendar.MILLISECOND)), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String date=LocalDate.now().format(formatter);
                System.err.println(date);

            }
        }), new KeyFrame(Duration.seconds(60)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
