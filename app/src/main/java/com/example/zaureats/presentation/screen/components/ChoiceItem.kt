package com.example.zaureats.presentation.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.zaureats.data.ChoiceData
import com.example.zaureats.presentation.viewmodel.MealViewModel

@Composable
fun ChoiceItem(
    choiceData: ChoiceData,
    mealViewModel: MealViewModel
) {

    val first = remember { mutableStateOf("") }
    val second = remember { mutableStateOf("") }
    val third = remember { mutableStateOf("") }
    val fourth = remember { mutableStateOf("") }
    val fifth = remember { mutableStateOf("") }
    val sixth = remember { mutableStateOf("") }
    val seventh = remember { mutableStateOf("") }

    val dataAndValues = listOf(
        first to choiceData.first,
        second to choiceData.second,
        third to choiceData.third,
        fourth to choiceData.fourth,
        fifth to choiceData.fifth,
        sixth to choiceData.sixth,
        seventh to choiceData.seventh
    )

    dataAndValues.forEach { (value, data) ->
        data?.forEach {
            value.value = it.key + "     +" + it.value.toString() + "zl"
        }
    }


    val radioOptions = listOf(
        first.value,
        second.value,
        third.value,
        fourth.value,
        fifth.value,
        sixth.value,
        seventh.value,
    ).filter { it.isNotBlank() }

    var selectedItem by rememberSaveable {
        mutableStateOf<String?>(radioOptions.first())
    }



    if (mealViewModel.selectedItem.contains(
            mapOf(choiceData.title.toString() to selectedItem)
        )
    ) {
        mealViewModel.selectedItem.remove(
            mapOf(choiceData.title.toString() to selectedItem)
        )
        selectedItem?.let { item ->
            mealViewModel.selectedItem.add(
                mapOf(choiceData.title.toString() to item)
            )
        }
    } else {
        selectedItem?.let { item ->
            mealViewModel.selectedItem.add(
                mapOf(choiceData.title.toString() to item)
            )
        }
    }

    Card {
        Column(modifier = Modifier.selectableGroup()) {
            radioOptions.forEach { label ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .selectable(
                            selected = (selectedItem == label),
                            onClick = {
                                selectedItem = label
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        modifier = Modifier.padding(end = 10.dp),
                        selected = (selectedItem == label),
                        onClick = {
                            selectedItem = label
                        }
                    )
                    Text(text = label)
                }
            }
        }
    }

}
