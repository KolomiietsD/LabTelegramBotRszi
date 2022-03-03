package com.example.labtelegrambot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramTestBotHandler extends TelegramLongPollingBot {

    @Override
    public String getBotToken(){
        return "5292637054:AAE07koQwEiC8w8FouJtH-6ZrsXYhVjKshI";
    }

    @Override
    public String getBotUsername(){
        return "KDrszi_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            try {
                execute(getResponse(update.getMessage()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private SendMessage getResponse(Message message) {
        String answer;
        if (message.getText().equals("Розклад")) {
            answer = getSchedule();
        } else if (message.getText().equals("Чим зайнятись?")) {
            answer = getActivity();
        } else {
            answer = "Я не знаю що ти від мене хочеш!";
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(answer);

        ReplyKeyboardMarkup markup = getReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(markup);

        return sendMessage;
    }

    private ReplyKeyboardMarkup getReplyKeyboardMarkup() {
            KeyboardRow searchRow = new KeyboardRow();
        searchRow.add("Розклад");
        searchRow.add("Чим зайнятись?");

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(searchRow);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    private String getSchedule() {
        LocalDate now = LocalDate.now();
        String schedule;
        if (now.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            schedule = "12:10 - Схемотехніка\n" +
                       "13:40 - Схемотехніка";
        } else if(now.getDayOfWeek().equals(DayOfWeek.TUESDAY)){
            schedule = "12:10 - Сучасні інформаційні системи та технології(л)\n" +
                       "13:40 - Розподілені системи збору інформації(л)\n";
        } else if(now.getDayOfWeek().equals(DayOfWeek.WEDNESDAY)){
            schedule = "10:30 - Розподілені системи збору інформації(п)\n" +
                       "13:40 - Менеджмент та маркетинг(л)\n" +
                       "15:10 - Теорія автоматичного управління(л)\n" +
                       "16:40 - Науковий образ світу(л)\n";
        } else if (now.getDayOfWeek().equals(DayOfWeek.THURSDAY)) {
            schedule = "13:40 - Теорія ймовірностей та комп'ютерна статистика(л)\n" +
                       "15:10 - Менеджмент та маркетинг(п)\n" +
                       "10:30 - Розподілені системи збору інформації(п)\n";
        }else if (now.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
            schedule = "13:40 - Схемотехніка(п)\n" +
                       "15:10 - Схемотехніка(п)\n" +
                       "16:40 - Сучасні інформаційні системи та технології(п)\n" +
                       "18:10 - Сучасні інформаційні системи та технології(п)\n";
        } else {
            schedule = "Вихідний день\n";
        }
        return schedule;
    }

    private String getActivity() {
        LocalDateTime now = LocalDateTime.now();
        String activity;
        if (now.getHour() <= 16 && now.getHour() >= 9) {
            activity = "Час навчатися";
        } else if (now.getHour() > 16 && now.getHour() < 18) {
            activity = "Розважайся";
        }  else if (now.getHour() >= 18 && now.getHour() < 23) {
            activity = "Відпочивай";
        } else if (now.getHour() < 9) {
            activity = "Думаю час поспати";
        } else {
            activity = "Біс його знає!";
        }
        return activity;
    }

}
