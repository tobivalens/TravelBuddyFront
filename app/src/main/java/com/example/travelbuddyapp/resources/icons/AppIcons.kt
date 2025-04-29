package com.example.travelbuddyapp.resources.icons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.travelbuddyapp.R

object AppIcons {
    @Composable
    fun Home(): ImageVector =
        ImageVector.vectorResource(R.drawable.home)

    @Composable
    fun HomeSelected(): ImageVector =
        ImageVector.vectorResource(R.drawable.homeselected)

    @Composable
    fun add(): ImageVector =
        ImageVector.vectorResource(R.drawable.add)

    @Composable
    fun addSelected(): ImageVector =
        ImageVector.vectorResource(R.drawable.addselected)

    @Composable
    fun profile(): ImageVector =
        ImageVector.vectorResource(R.drawable.profile)

    @Composable
    fun profileSelected(): ImageVector =
        ImageVector.vectorResource(R.drawable.profileselected)

    @Composable
    fun search(): ImageVector =
        ImageVector.vectorResource(R.drawable.search)
}
