package qna.domain;

import org.junit.Before;
import org.junit.Test;
import qna.CannotDeleteException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {
    private Answer answer;

    @Before
    public void setUp() {
        answer = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    }

    @Test
    public void delete_성공() throws Exception {
        assertThat(answer.isDeleted()).isFalse();
        final DeleteHistory deleteHistory = answer.delete(UserTest.JAVAJIGI);

        assertThat(answer.isDeleted()).isTrue();
        verify(deleteHistory);
    }

    private void verify(DeleteHistory deleteHistory) {
        final DeleteHistory expected =
                new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(), LocalDateTime.now());

        assertThat(deleteHistory).isEqualTo(expected);
    }

    @Test
    public void delete_다른_사람이_쓴_답변() {
        assertThatThrownBy(() -> {
            answer.delete(UserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }
}
