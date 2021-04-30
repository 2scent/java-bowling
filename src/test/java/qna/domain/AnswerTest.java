package qna.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {
    public static final Answer A1 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(UserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    private Answer answer;

    @Before
    public void setUp() {
        answer = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    }

    @Test
    public void delete() {
        assertThat(answer.isDeleted()).isFalse();
        final DeleteHistory deleteHistory = answer.delete();

        assertThat(answer.isDeleted()).isTrue();
        verify(deleteHistory);
    }

    private void verify(DeleteHistory deleteHistory) {
        final DeleteHistory expected =
                new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(), LocalDateTime.now());

        assertThat(deleteHistory).isEqualTo(expected);
    }
}
