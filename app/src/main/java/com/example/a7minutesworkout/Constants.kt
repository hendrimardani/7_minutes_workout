package com.example.a7minutesworkout

object Constants {
    fun defaultArrayList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(
            1,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false
        )
        exerciseList.add(jumpingJacks)

        val abdominalCrunch = ExerciseModel(
            2,
            "Abnomial Crunch",
            R.drawable.ic_abdominal_crunch,
            false,
            false
        )
        exerciseList.add(abdominalCrunch)

        val highKnee = ExerciseModel(
            3,
            "High Knee",
            R.drawable.ic_high_knees_running_in_place,
            false,
            false
        )
        exerciseList.add(highKnee)

        val lunge = ExerciseModel(
            4,
            "Lunge",
            R.drawable.ic_lunge,
            false,
            false
        )
        exerciseList.add(lunge)

        val plank = ExerciseModel(
            5,
            "Plank",
            R.drawable.ic_plank,
            false,
            false
        )
        exerciseList.add(plank)

        val pushUp = ExerciseModel(
            5,
            "Push Up",
            R.drawable.ic_push_up,
            false,
            false
        )
        exerciseList.add(pushUp)

        val pushUpRotation = ExerciseModel(
            6,
            "Push Up Rotation",
            R.drawable.ic_push_up_and_rotation,
            false,
            false
        )
        exerciseList.add(pushUpRotation)

        val squat = ExerciseModel(
            7,
            "Squat",
            R.drawable.ic_squat,
            false,
            false
        )
        exerciseList.add(squat)

        val stepUpOntoChair = ExerciseModel(
            8,
            "Step Up Onto Chair",
            R.drawable.ic_step_up_onto_chair,
            false,
            false
        )
        exerciseList.add(stepUpOntoChair)

        val tricepsDipOnChair = ExerciseModel(
            9,
            "Triceps Dip On Chair",
            R.drawable.ic_triceps_dip_on_chair,
            false,
            false
        )
        exerciseList.add(tricepsDipOnChair)

        val wallSit = ExerciseModel(
            10,
            "Wall Sit",
            R.drawable.ic_wall_sit,
            false,
            false
        )
        exerciseList.add(wallSit)

        return  exerciseList
    }
}