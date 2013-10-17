package net.peakgames.libgdx.stagebuilder.core.builder;


import com.badlogic.gdx.scenes.scene2d.utils.Align;
import net.peakgames.libgdx.stagebuilder.core.builder.ActorBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LabelBuilderTest {

    @Test
    public void calculateAlignment() {
        int alignment = ActorBuilder.calculateAlignment("center");
        assertEquals(alignment, Align.center);

        alignment = ActorBuilder.calculateAlignment("center|top");
        assertEquals(alignment, Align.center|Align.top);

        alignment = ActorBuilder.calculateAlignment("left|center|top");
        assertEquals(alignment, Align.left|Align.center|Align.top);

        alignment = ActorBuilder.calculateAlignment("center|top|right");
        assertEquals(alignment, Align.center|Align.top|Align.right);
    }
}
