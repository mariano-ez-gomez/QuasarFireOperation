package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.exceptions.QuasarFireOperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import java.util.ArrayList;

public class MessageDeterminationServiceImplTest {

    private static final String DECODED_MESSAGE = "este es un mensaje secreto";

    private ArrayList<String[]> messages_empty_or_null = new ArrayList<>();
    private ArrayList<String[]> messages_incorrect_lengths = new ArrayList<>();
    private ArrayList<String[]> messages_incorrect_blank_word = new ArrayList<>();
    private ArrayList<String[]> messages_correct = new ArrayList<>();
    private ArrayList<String[]> messages_correct_initial_interference = new ArrayList<>();

    @Spy
    private MessageDeterminationServiceImpl messageDeterminationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        String[] message_empty = {};
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

    @Test
    public void getMessage_empty_or_null_messages() {
        Assertions.assertThrows(QuasarFireOperationException.class, () -> this.messageDeterminationService.getMessage(messages_empty_or_null));
    }

    @Test
    public void getMessage_incorrect_lengths() {
        Assertions.assertThrows(QuasarFireOperationException.class, () -> this.messageDeterminationService.getMessage(messages_incorrect_lengths));
    }

    @Test
    public void getMessage_incorrect_blank_word() {
        Assertions.assertThrows(QuasarFireOperationException.class, () -> this.messageDeterminationService.getMessage(messages_incorrect_blank_word));
    }

    @Test
    public void getMessage_correct() throws QuasarFireOperationException {
        String decodedMessage = this.messageDeterminationService.getMessage(messages_correct);
        Assertions.assertEquals(DECODED_MESSAGE, decodedMessage);
    }

    @Test
    public void getMessage_correct_with_initial_interference() throws QuasarFireOperationException {
        String decodedMessage = this.messageDeterminationService.getMessage(messages_correct_initial_interference);
        Assertions.assertEquals(DECODED_MESSAGE, decodedMessage);
    }
}