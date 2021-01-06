package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.exceptions.SatelliteException;
import com.mercadolibre.quasar_fire_operation.services.MessageDeterminationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class MessageDeterminationServiceImpl implements MessageDeterminationService {

    private static final String MESSAGES_EXCEPTION = "Cannot determine message";
    private static final int NUMBER_OF_SATELLITES = 3;
    public static final String BLANK_SPACE = " ";
    public static final String EMPTY_STRING = "";

    @Override
    public String getMessage(ArrayList<String[]> messages) throws SatelliteException {
        String decodedMessage = "";

        this.validateMessages(messages);
        int minLength = getMinimumMessageLength(messages);

        String[] msg0 = messages.get(0);
        String[] msg1 = messages.get(1);
        String[] msg2 = messages.get(2);

        decodedMessage = this.decodeMessage(decodedMessage, minLength, msg0, msg1, msg2);

        return decodedMessage;
    }

    private String decodeMessage(String decodedMessage, int minLength, String[] message0, String[] message1, String[] message2) throws SatelliteException {
        ArrayList<String> words = new ArrayList<>();
        for(int i = 0; i < minLength; i++) {
            words.add(message0[message0.length > minLength ? i+1 : i]);
            words.add(message1[message1.length > minLength ? i+1 : i]);
            words.add(message2[message2.length > minLength ? i+1 : i]);

            if(this.areAllDifferentWordsOrBlankSpaces(words)) {
                //all the words are different or are blank spaces
                throw new SatelliteException(MESSAGES_EXCEPTION);
            } else {
                decodedMessage = this.appendWord(decodedMessage, this.findWord(words));
            }
            words = new ArrayList<>();
        }
        return decodedMessage.trim();
    }

    private boolean areAllDifferentWordsOrBlankSpaces(ArrayList<String> words) {
        return filterBlankSpaces(words).distinct().count() > 1 ;
    }

    private String findWord(ArrayList<String> words) {
        //returns the first word that is not a blank space
        return filterBlankSpaces(words).findFirst().orElse(null);
    }

    private Stream<String> filterBlankSpaces(ArrayList<String> words) {
        return words.stream().filter(word -> !BLANK_SPACE.equals(word) && !EMPTY_STRING.equals(word));
    }

    private int getMinimumMessageLength(ArrayList<String[]> messages) {
        int minLength = messages.get(0).length;
        for(int i = 1; i < NUMBER_OF_SATELLITES; i++) {
            if(messages.get(i).length < minLength) {
                minLength = messages.get(i).length;
            }
        }
        return minLength;
    }

    private void validateMessages(ArrayList<String[]> messages) throws SatelliteException {
        if(messages.stream().anyMatch(mes -> Objects.isNull(mes) || mes.length == 0)) {
            //at least one message is empty
            throw new SatelliteException(MESSAGES_EXCEPTION);
        }
    }

    private String appendWord(String decodedMessage, String word) throws SatelliteException {
        if(Objects.nonNull(word)) {
            return decodedMessage.concat(word + BLANK_SPACE);
        } else {
            throw new SatelliteException(MESSAGES_EXCEPTION);
        }
    }

}
