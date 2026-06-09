import hr.dtakac.prognoza.shared.entity.BeaufortScale
import hr.dtakac.prognoza.shared.entity.Speed
import hr.dtakac.prognoza.shared.entity.SpeedUnit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SpeedTest { // Not Ookla ;)
    // UT-047
    @Test
    fun `throws exception when less than 0`() {
        assertFailsWith<IllegalStateException> {
            Speed(-20.0, SpeedUnit.METRE_PER_SECOND)
        }
    }

    // UT-048
    @Test
    fun `converts meters per second to others`() {
        val speed = Speed(2.0, SpeedUnit.METRE_PER_SECOND)
        assertSpeedsAreAsExpected(
            speed = speed,
            expectedMps = 2.0,
            expectedKmh = 7.2,
            expectedMph = 4.4738,
            expectedBeaufort = BeaufortScale.LIGHT_BREEZE,
            expectedKt = 3.8876
        )
    }

    // UT-049
    @Test
    fun `converts miles per hour to others`() {
        val speed = Speed(2.0, SpeedUnit.MILE_PER_HOUR)
        assertSpeedsAreAsExpected(
            speed = speed,
            expectedMps = 0.8940,
            expectedKmh = 3.2187,
            expectedMph = 2.0,
            expectedBeaufort = BeaufortScale.LIGHT_AIR,
            expectedKt = 1.7380
        )
    }

    // UT-050
    @Test
    fun `converts kilometers per hour to others`() {
        val speed = Speed(2.0, SpeedUnit.KILOMETRE_PER_HOUR)
        assertSpeedsAreAsExpected(
            speed = speed,
            expectedMps = 0.5556,
            expectedKmh = 2.0,
            expectedMph = 1.2427,
            expectedBeaufort = BeaufortScale.LIGHT_AIR,
            expectedKt = 1.0799
        )
    }

    // UT-051
    @Test
    fun `converts knots to others`() {
        val speed = Speed(2.0, SpeedUnit.KNOT)
        assertSpeedsAreAsExpected(
            speed = speed,
            expectedMps = 1.0289,
            expectedKmh = 3.7040,
            expectedMph = 2.3016,
            expectedBeaufort = BeaufortScale.LIGHT_AIR,
            expectedKt = 2.0
        )
    }

    // UT-057
    @Test
    fun `allows zero speed`() {
        val speed = Speed(0.0, SpeedUnit.METRE_PER_SECOND)
        assertSpeedsAreAsExpected(
            speed = speed,
            expectedMps = 0.0,
            expectedKmh = 0.0,
            expectedMph = 0.0,
            expectedBeaufort = BeaufortScale.CALM,
            expectedKt = 0.0
        )
    }

    // UT-058
    @Test
    fun `maps exact Beaufort thresholds to next scale`() {
        val mphToExpectedScale = mapOf(
            1.0 to BeaufortScale.LIGHT_AIR,
            3.0 to BeaufortScale.LIGHT_BREEZE,
            7.0 to BeaufortScale.GENTLE_BREEZE,
            12.0 to BeaufortScale.MODERATE_BREEZE,
            18.0 to BeaufortScale.FRESH_BREEZE,
            24.0 to BeaufortScale.STRONG_BREEZE,
            31.0 to BeaufortScale.NEAR_GALE,
            38.0 to BeaufortScale.GALE,
            46.0 to BeaufortScale.SEVERE_GALE,
            54.0 to BeaufortScale.STORM,
            63.0 to BeaufortScale.VIOLENT_STORM,
            72.0 to BeaufortScale.HURRICANE
        )

        mphToExpectedScale.forEach { (mph, expectedScale) ->
            assertEquals(
                expected = expectedScale,
                actual = Speed(mph, SpeedUnit.MILE_PER_HOUR).beaufortScale
            )
        }
    }

    private fun assertSpeedsAreAsExpected(
        speed: Speed,
        expectedMps: Double,
        expectedKmh: Double,
        expectedMph: Double,
        expectedKt: Double,
        expectedBeaufort: BeaufortScale
    ) {
        val tolerance = 0.0001
        assertEquals(
            expected = expectedKt,
            actual = speed.knot,
            absoluteTolerance = tolerance
        )
        assertEquals(
            expected = expectedMph,
            actual = speed.milePerHour,
            absoluteTolerance = tolerance
        )
        assertEquals(
            expected = expectedMps,
            actual = speed.metrePerSecond,
            absoluteTolerance = tolerance
        )
        assertEquals(
            expected = expectedKmh,
            actual = speed.kilometrePerHour,
            absoluteTolerance = tolerance
        )
        assertEquals(
            expected = expectedBeaufort,
            actual = speed.beaufortScale
        )
    }
}
