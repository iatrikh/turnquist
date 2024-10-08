package learning.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CoreDomainTest {

    @Test
    void newVideoEntityShouldHaveNullId() {

        VideoEntity entity = new VideoEntity("alice", "title", "description");

        assertThat(entity.getId()).isNull();
        assertThat(entity.getUsername()).isEqualTo("alice");
        assertThat(entity.getName()).isEqualTo("title");
        assertThat(entity.getDescription()).isEqualTo("description");
    }

    @Test
    void toStringShouldAlsoBeTested() {
        VideoEntity entity = new VideoEntity("alice", "title", "description");
        assertThat(entity.toString())
                .isEqualTo("VideoEntity{id=null, username='alice', name='title', description='description'}");
    }

    @Test
    void settersShouldMutateState() {
        VideoEntity entity = new VideoEntity("alice", "title", "description");
        entity.setId(99L);
        entity.setUsername("bob");
        entity.setName("new name");
        entity.setDescription("new desc");

        assertThat(entity.getId()).isEqualTo(99L);
        assertThat(entity.getUsername()).isEqualTo("bob");
        assertThat(entity.getName()).isEqualTo("new name");
        assertThat(entity.getDescription()).isEqualTo("new desc");
    }

    @Test
    void testVideoEntityNoArgumentConstructor() {
        var entity = new VideoEntity();

        assertThat(entity.getId()).isNull();
        assertThat(entity.getUsername()).isNull();
        assertThat(entity.getName()).isNull();
        assertThat(entity.getDescription()).isNull();
    }
}
