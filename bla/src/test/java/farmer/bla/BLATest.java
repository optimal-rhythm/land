package farmer.bla;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BLATest {

  private BLA bla;
  
  @Before
  public void setUp() throws Exception {
    this.bla = new BLA();
  }

  @After
  public void tearDown() throws Exception {
    this.bla = null;
  }

  @Test
  public void testFindFertileAreas() {
    String[] br = new String[]{"0 292 399 307"};
    bla.setBarrenRegions(br);
    List<Integer> fertileAreas = bla.computeFertileRegions();
    
    assertNotNull(fertileAreas);
    assertEquals(2, fertileAreas.size());
    assertEquals(116800, fertileAreas.get(0).intValue());
    assertEquals(116800, fertileAreas.get(1).intValue());
    
    br = new String[]{"48 192 351 207","48 392 351 407", "120 52 135 547", "260 52 275 547"};
    
    bla = new BLA();
    bla.setBarrenRegions(br);
    fertileAreas = bla.computeFertileRegions();
    
    assertNotNull(fertileAreas);
    assertEquals(2, fertileAreas.size());
    assertEquals(22816, fertileAreas.get(0).intValue());
    assertEquals(192608, fertileAreas.get(1).intValue());
    
    bla = new BLA();
    br = new String[]{"0 0 399 599"};    
    bla.setBarrenRegions(br);
    fertileAreas = bla.computeFertileRegions();
    
    assertNotNull(fertileAreas);
    assertEquals(0, fertileAreas.size());
    
    bla = new BLA();
    br = new String[]{"0 0 1 1"};
    bla.setBarrenRegions(br);
    fertileAreas = bla.computeFertileRegions();
    assertEquals(1, fertileAreas.size());
    assertEquals(239996, fertileAreas.get(0).intValue());

    bla = new BLA();
    br = new String[]{"0 0 0 599"};
    bla.setBarrenRegions(br);
    fertileAreas = bla.computeFertileRegions();
    assertEquals(1, fertileAreas.size());
    assertEquals(239400, fertileAreas.get(0).intValue());

    bla = new BLA();
    br = new String[]{"0 0 1 599", "5 0 5 599"};
    bla.setBarrenRegions(br);
    fertileAreas = bla.computeFertileRegions();
    assertEquals(2, fertileAreas.size());
    assertEquals(1800, fertileAreas.get(0).intValue());
    assertEquals(236400, fertileAreas.get(1).intValue());

    bla = new BLA();
    br = new String[]{"0 0 0 599", "0 0 399 0", "399 0 399 599", "0 599 399 599"};
    bla.setBarrenRegions(br);
    fertileAreas = bla.computeFertileRegions();
    assertEquals(1, fertileAreas.size());
    assertEquals(238004, fertileAreas.get(0).intValue());

    bla = new BLA();
    br = new String[]{"10 5 10 599", "2 0 2 599", "3 3 399 3", "5 10 5 15", "10 4 20 599"};
    bla.setBarrenRegions(br);
    fertileAreas = bla.computeFertileRegions();
    System.out.println(fertileAreas);
    assertEquals(4, fertileAreas.size());
    assertEquals(1191, fertileAreas.get(0).intValue());
    assertEquals(1200, fertileAreas.get(1).intValue());
    assertEquals(4166, fertileAreas.get(2).intValue());
    assertEquals(225884, fertileAreas.get(3).intValue());
  }
  
  @Test
  public void testcheckCorners_success() {
    bla.checkCorners(0, 292, 399, 307);
  }
  
  @Test
  (expected = IllegalArgumentException.class)
  public void testcheckCorners_fail1() {
    bla.checkCorners(0, -292, 399, 307);
  }
  
  @Test
  (expected = IllegalArgumentException.class)
  public void testcheckCorners_fail2() {
    bla.checkCorners(0, 292, 281, 291);
  }
  
  @Test
  (expected = IllegalArgumentException.class)
  public void testcheckCorners_fail3() {
    bla.checkCorners(1, 292, 0, 307);
  }
  
  @Test
  (expected = IllegalArgumentException.class)
  public void testcheckCorners_fail4() {
    bla.checkCorners(48, 392, 30, 407);
  }
  
  @Test
  (expected = IllegalArgumentException.class)
  public void testcheckCorners_fail5() {
    bla.checkCorners(0, 292, 400, 307);
  }
  
  @Test
  (expected = IllegalArgumentException.class)
  public void testcheckCorners_fail6() {
    bla.checkCorners(0, 292, 399, 600);
  }
  
  
}