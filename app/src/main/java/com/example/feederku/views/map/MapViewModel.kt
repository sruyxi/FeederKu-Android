package com.example.feederku.views.map

import androidx.lifecycle.ViewModel
import com.example.feederku.views.map.model.Sighting
import com.example.feederku.views.map.model.SightingCategory
import org.maplibre.android.geometry.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MapUiState(
    val sightings: List<Sighting> = emptyList(),
    val selectedSighting: Sighting? = null,
    val isReporting: Boolean = false,
    val reportAddress: String = "",
    val reportCategory: SightingCategory? = null,
    val reportNotes: String = "",
    val isPlacingSighting: Boolean = false,
    val pendingSightingLocation: LatLng? = null,
    val selectedFilter: String = "Urgent Spots",
    val isShowingRoute: Boolean = false,
    val routeDestination: LatLng? = null
)

class MapViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState(
        sightings = listOf(
            Sighting(
                id = "1",
                title = "Supermal Karawaci",
                location = LatLng(-6.2215, 106.6135),
                distance = "300m away",
                category = SightingCategory.MANY_CATS,
                status = "HUNGRY",
                description = "Group of stray cats near main entrance parking looking for food.",
                address = "Supermal Karawaci, Lippo Karawaci"
            ),
            Sighting(
                id = "2",
                title = "SPM Lippo Village",
                location = LatLng(-6.2268, 106.6089),
                distance = "650m away",
                category = SightingCategory.URGENT_HELP,
                status = "URGENT",
                description = "Injured kitten spotted near SPM school gate. Needs immediate medical checkup.",
                address = "SPM Lippo Village, Karawaci"
            ),
            Sighting(
                id = "3",
                title = "UPH Campus Gate",
                location = LatLng(-6.2321, 106.5998),
                distance = "1.2 km away",
                category = SightingCategory.ALREADY_FED,
                status = "FED",
                description = "Fed by campus community earlier this morning. Well fed and resting.",
                address = "Universitas Pelita Harapan (UPH)"
            ),
            Sighting(
                id = "4",
                title = "Benton Junction",
                location = LatLng(-6.2250, 106.6110),
                distance = "500m away",
                category = SightingCategory.URGENT_HELP,
                status = "URGENT",
                description = "Estimated 4 cats reported here near Family Mart. Haven't been fed since yesterday.",
                address = "Benton Junction, Lippo Karawaci"
            ),
            Sighting(
                id = "5",
                title = "Maxxbox Karawaci",
                location = LatLng(-6.2198, 106.6050),
                distance = "1.5 km away",
                category = SightingCategory.VET,
                status = "OPEN",
                description = "Nearby partner vet clinic open for emergency checkups.",
                address = "Maxxbox Karawaci"
            )
        )
    ))
    val uiState = _uiState.asStateFlow()

    fun onSightingClick(sighting: Sighting) {
        _uiState.update { it.copy(selectedSighting = sighting, isReporting = false, isShowingRoute = false) }
    }

    fun onDismissSighting() {
        _uiState.update { it.copy(selectedSighting = null, isShowingRoute = false) }
    }

    fun onReportClick() {
        _uiState.update { it.copy(isReporting = true, selectedSighting = null, isShowingRoute = false) }
    }

    fun onDismissReport() {
        _uiState.update { it.copy(isReporting = false, isPlacingSighting = false) }
    }

    fun onStartPlacingSighting() {
        _uiState.update { it.copy(isPlacingSighting = true, isReporting = false) }
    }

    fun onMapClick(latLng: LatLng) {
        if (_uiState.value.isPlacingSighting) {
            _uiState.update { 
                it.copy(
                    pendingSightingLocation = latLng, 
                    isReporting = true, 
                    isPlacingSighting = false,
                    reportAddress = String.format("Karawaci Area (%.4f, %.4f)", latLng.latitude, latLng.longitude)
                ) 
            }
        } else {
            onDismissSighting()
        }
    }

    fun getSightingById(locationId: String): Sighting {
        return uiState.value.sightings.find { it.id == locationId }
            ?: uiState.value.selectedSighting
            ?: Sighting(
                id = locationId,
                title = "Supermal Karawaci",
                location = LatLng(-6.2215, 106.6135),
                distance = "300m",
                category = SightingCategory.MANY_CATS,
                description = "Stray cats spotted near entrance.",
                status = "HUNGRY",
                address = "Supermal Karawaci"
            )
    }

    fun onReportAddressChange(address: String) {
        _uiState.update { it.copy(reportAddress = address) }
    }

    fun onReportCategorySelect(category: SightingCategory) {
        _uiState.update { it.copy(reportCategory = category) }
    }

    fun onReportNotesChange(notes: String) {
        if (notes.length <= 200) {
            _uiState.update { it.copy(reportNotes = notes) }
        }
    }

    fun onFilterSelect(filter: String) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }

    fun onGetRouteClick() {
        val loc = _uiState.value.selectedSighting?.location
        _uiState.update { it.copy(isShowingRoute = true, routeDestination = loc) }
    }

    fun onSubmitReport() {
        val newSighting = Sighting(
            id = System.currentTimeMillis().toString(),
            title = _uiState.value.reportCategory?.label ?: "New Sighting Spot",
            location = _uiState.value.pendingSightingLocation ?: LatLng(-6.225, 106.611),
            distance = "Nearby",
            category = _uiState.value.reportCategory ?: SightingCategory.MANY_CATS,
            description = _uiState.value.reportNotes.ifBlank { "Reported stray cat sighting in Karawaci area." },
            status = if (_uiState.value.reportCategory == SightingCategory.URGENT_HELP) "URGENT" else "HUNGRY",
            address = _uiState.value.reportAddress.ifBlank { "Lippo Karawaci, Tangerang" }
        )
        _uiState.update { 
            it.copy(
                sightings = it.sightings + newSighting,
                isReporting = false, 
                reportAddress = "", 
                reportCategory = null, 
                reportNotes = "",
                pendingSightingLocation = null,
                selectedSighting = newSighting
            ) 
        }
    }
}
