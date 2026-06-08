import hr.dtakac.prognoza.shared.entity.Pressure
import hr.dtakac.prognoza.shared.entity.PressureUnit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PressureTest {
    private val tolerance = 0.0001

    // UT-001
    @Test
    fun `throws exception when negative`() {
        assertFailsWith<IllegalStateException> {
            Pressure(-98.0, PressureUnit.MILLIBAR)
        }
    }

    // UT-002
    @Test
    fun `converts from millibar to inches of mercury`() = assertEquals(
        expected = 0.0886,
        actual = Pressure(3.0, PressureUnit.MILLIBAR).inchOfMercury,
        absoluteTolerance = tolerance
    )

    // UT-003
    @Test
    fun `converts from inches of mercury to millibar`() = assertEquals(
        expected = 101.5917,
        actual = Pressure(3.0, PressureUnit.INCH_OF_MERCURY).millibar,
        absoluteTolerance = tolerance
    )
}
