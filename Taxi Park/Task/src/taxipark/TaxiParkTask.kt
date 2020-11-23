package taxipark

/*
 * Task #1. Find all the drivers who performed  no trips.
 */

fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.filter { it -> it.name !in trips.map { it.driver.name } }.toSet()
/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        allPassengers.filter { passenger -> trips.filter { trip -> passenger in trip.passengers }.count() >= minTrips }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        allPassengers.filter { passenger -> trips.filter { trip -> driver == trip.driver }.filter { it -> passenger in it.passengers }.count() > 1 }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        allPassengers.filter { passenger ->
            trips.filter { trip -> trip.discount != null && passenger in trip.passengers }.count() >
                    trips.filter { trip -> trip.discount == null && passenger in trip.passengers }.count()
        }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    val tripRangeDuration: Int? = trips.map { it.duration }
            .groupBy { it / 10 }
            .maxBy { it.value.size }
            ?.key

    return tripRangeDuration?.let { it ->
        val startNum = it * 10
        val endNum = startNum + 9
        IntRange(startNum, endNum)
    }
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false
    val totalIncome80: Double = trips.map { it.cost }.sumByDouble { it } * 0.8
    val driverCount20 = (allDrivers.size * 0.2).toInt()

    val ffmap = mutableMapOf<Driver, Double>()

    val s = trips.groupBy { it.driver }.values
    for (i in s) {
        var driver: Driver? = null
        var sum = 0.0
        for (ii in i) {
            sum += ii.cost
            driver = ii.driver
        }
        if (driver != null) {
            ffmap.put(driver, sum)
        }
    }

    val son = ffmap.toList().sortedBy { (_, value) -> value }.reversed().take(driverCount20)

    return son.sumByDouble { it.second } >= totalIncome80
}
