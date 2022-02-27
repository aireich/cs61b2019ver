package bearmaps.test;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestClosest {

    @Test
    public void testClose() {
        AugmentedStreetMapGraph ag = new AugmentedStreetMapGraph("../library-sp19/data/proj2c_xml/berkeley-2019.osm.xml");
        long near = ag.closest(37.875613, -122.26009);
        assertEquals(1281866063, near);

    }

}
