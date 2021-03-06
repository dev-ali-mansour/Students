package dev.alimansour.students.ui.activity;

import dev.alimansour.students.model.Student;

/**
 * Students Android Application developed by: Ali Mansour
 * Copyright © 2020 Ali Mansour. All Rights Reserved.
 * This file may not be redistributed in whole or significant part.
 * ----------------- Students IS FREE SOFTWARE ------------------
 * https://www.alimansour.dev   |   dev.ali.mansour@gmail.com
 */
public interface OnCLickListener {

    public void onClick(int Id);

    public void onDelete(Student student);
}
