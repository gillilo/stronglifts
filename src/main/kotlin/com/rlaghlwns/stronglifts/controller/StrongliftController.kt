package com.rlaghlwns.stronglifts.controller

import com.rlaghlwns.stronglifts.dto.OneRmRequest
import com.rlaghlwns.stronglifts.model.WorkoutSchedule
import com.rlaghlwns.stronglifts.service.StrongliftsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Positive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stronglifts")
@Tag(name = "Stronglifts", description = "Stronglifts schedule API")
class StrongliftsController {

    @Autowired
    final lateinit var strongliftsService: StrongliftsService

    @PostMapping("/schedule")
    @Operation(summary = "Get stronglifts schedule", description = "사용자 중량에 맞는 운동 스케줄을 반환한다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공")
    ])
    fun getWorkoutSchedule(
        @Schema(implementation = OneRmRequest::class)
        @RequestBody request: OneRmRequest
    ): List<WorkoutSchedule> {
        return strongliftsService.generateSchedule(request, 12, false)
    }

    @PostMapping("/schedule/{week}")
    @Operation(summary = "Get stronglifts week session schedule", description = "사용자 중량에 맞는 특정 주차 운동 스케줄을 반환한다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공")
    ])
    fun getWeeklySchedule(
        @Schema(implementation = OneRmRequest::class)
        @RequestBody request: OneRmRequest,
        @Parameter(name = "week", description = "운동 스케줄 주차 값", example = "7", required = true)
        @Positive(message = "week는 양수입니다.")
        @PathVariable week: Int
    ): WorkoutSchedule {
        require(!(week < 1 || week > 12)) { "Week must be between 1 and 12" }
        val schedules: List<WorkoutSchedule> = strongliftsService.generateSchedule(request, 12, false)
        return schedules[week - 1]
    }

    @PostMapping("/deload/{week}")
    @Operation(summary = "Get stronglifts deload schedule", description = "사용자 중량에 맞는 디로딩 스케줄을 반환한다.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "성공")
    ])
    fun getDeloadSchedule(
        @Schema(implementation = OneRmRequest::class)
        @RequestBody request: OneRmRequest,
        @Parameter(name = "week", description = "운동 스케줄 주차 값", example = "7", required = true)
        @Positive(message = "week는 양수입니다.")
        @PathVariable week: Int
    ): WorkoutSchedule {
        require(!(week < 1 || week > 12)) { "Week must be between 1 and 12" }
        val schedules: List<WorkoutSchedule> = strongliftsService.generateSchedule(request, 12, true)
        return schedules[week - 1]
    }
}