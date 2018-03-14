import com.sun.xml.internal.fastinfoset.tools.TransformInputOutput

class Schedule() {
    private val trains = mutableMapOf<String, Train>()

    private class Train(val name: String, val departureTime: Int, val terminal: String) {
        private val intermediateStations = mutableListOf<String>()

        fun addStation(nameStation: String) {
            if (nameStation !in intermediateStations) intermediateStations.add(nameStation)
        }

        fun removeStation(nameStation: String) {
            intermediateStations.remove(nameStation)
        }

        fun getIntermediateStations(): List<String> = intermediateStations
    }

    fun addTrain(nameTrain: String, departureTime: Int, terminal: String) {
        if (nameTrain == "") throw IllegalArgumentException()
        trains.put(nameTrain, Train(nameTrain, departureTime, terminal))
    }

    fun removeTrain(nameTrain: String) {
        trains.remove(nameTrain)
    }

    fun addStation(nameTrain: String, nameStation: String) {
        trains[nameTrain]!!.addStation(nameStation)
    }

    fun remoteStation(nameTrain: String, nameStation: String) {
        trains[nameTrain]!!.removeStation(nameStation)
    }

    fun findTrain(terminal: String, time: Int): String {
        var minTime = Int.MAX_VALUE
        var result = ""
        var trainNotFound = true
        for (train in trains) {
            if (terminal in train.value.getIntermediateStations() || terminal == train.value.terminal) {
                val deltaTime = train.value.departureTime - time
                if (deltaTime in 0..minTime) { //if (deltaTime >= 0 && deltaTime <= minTime)
                    result = train.value.name
                    minTime = deltaTime
                    trainNotFound = false
                }
            }
        }
        if (trainNotFound) throw IllegalArgumentException()
        else return result
    }

}

