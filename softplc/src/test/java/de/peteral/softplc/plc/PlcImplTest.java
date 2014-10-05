package de.peteral.softplc.plc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.peteral.softplc.model.Cpu;
import de.peteral.softplc.model.Plc;
import de.peteral.softplc.model.PutGetServer;
import de.peteral.softplc.plc.PlcImpl;

@SuppressWarnings("javadoc")
public class PlcImplTest {

	private Plc plc;

	@Mock
	private Cpu cpu1;
	@Mock
	private Cpu cpu2;
	@Mock
	private PutGetServer server;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		when(cpu2.getSlot()).thenReturn(1);
		Cpu[] cpus = { cpu1, cpu2 };

		plc = new PlcImpl(server, cpus);
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void getCpu_InvalidSlot_ThrowsException() {
		plc.getCpu(2);
	}

	@Test
	public void getCpu_ValidSlot_ReturnsCorrectInstance() {
		assertSame(cpu2, plc.getCpu(1));
	}

	@Test
	public void getCpuCount_None_ReturnsCorrectCount() {
		assertEquals(2, plc.getCpuCount());
	}

	@Test
	public void start_None_StartsAllCpus() {
		plc.start();

		verify(cpu1).start();
		verify(cpu2).start();
	}

	@Test
	public void start_None_StartsPutGetServer() {
		plc.start();

		verify(server).start();
	}

	@Test
	public void stop_None_StopsPutGetServer() {
		plc.stop();

		verify(server).stop();
	}

	@Test
	public void stop_None_StopsAllCpus() {
		plc.stop();

		verify(cpu1).stop();
		verify(cpu2).stop();
	}
}
