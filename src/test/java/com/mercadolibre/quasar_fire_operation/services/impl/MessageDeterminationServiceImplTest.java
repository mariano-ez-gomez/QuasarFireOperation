package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.exceptions.SatelliteException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class MessageDeterminationServiceImplTest {

    private static final String DECODED_MESSAGE = "este es un mensaje secreto";

    private ArrayList<String[]> messages_empty_or_null = new ArrayList<>();
    private ArrayList<String[]> messages_incorrect_lengths = new ArrayList<>();
    private ArrayList<String[]> messages_incorrect_blank_word = new ArrayList<>();
    private ArrayList<String[]> messages_correct = new ArrayList<>();
    private ArrayList<String[]> messages_correct_initial_interference = new ArrayList<>();

    @InjectMocks
    @Spy
    private MessageDeterminationServiceImpl messageDeterminationService;

    @Before
    public void init() {
        String[] message_empty = {};
        messages_empty_or_null.add(null);
        messages_empty_or_null.add(message_empty);
        messages_empty_or_null.add(null);

        messages_incorrect_lengths.add(new String[] {"","es"});
        messages_incorrect_lengths.add(new String[] {"este"});
        messages_incorrect_lengths.add(new String[] {"","es","un"});

        messages_incorrect_blank_word.add(new String[] {"este", "", "un"});
        messages_incorrect_blank_word.add(new String[] {"este", "", "un"});
        messages_incorrect_blank_word.add(new String[] {"este", "", "un"});

        messages_correct.add(new String[] {"este", "", "", "mensaje", ""});
        messages_correct.add(new String[] {"","es", "", "", "secreto"});
        messages_correct.add(new String[] {"", "", "un", "", ""});

        messages_correct_initial_interference.add(new String[] {"", "este", "", "", "mensaje", ""});
        messages_correct_initial_interference.add(new String[] {"","es", "", "", "secreto"});
        messages_correct_initial_interference.add(new String[] {"", "", "", "un", "", ""});
    }

    @Test(expected = SatelliteException.class)
    public void getMessage_empty_or_null_messages() throws SatelliteException {
        this.messageDeterminationService.getMessage(messages_empty_or_null);
    }

    @Test(expected = SatelliteException.class)
    public void getMessage_incorrect_lengths() throws SatelliteException {
        this.messageDeterminationService.getMessage(messages_incorrect_lengths);
    }

    @Test(expected = SatelliteException.class)
    public void getMessage_incorrect_blank_word() throws SatelliteException {
        this.messageDeterminationService.getMessage(messages_incorrect_blank_word);
    }

    @Test
    public void getMessage_correct() throws SatelliteException {
        String decodedMessage = this.messageDeterminationService.getMessage(messages_correct);
        Assert.assertEquals(DECODED_MESSAGE, decodedMessage);
    }

    @Test
    public void getMessage_correct_with_initial_interference() throws SatelliteException {
        String decodedMessage = this.messageDeterminationService.getMessage(messages_correct_initial_interference);
        Assert.assertEquals(DECODED_MESSAGE, decodedMessage);
    }
}