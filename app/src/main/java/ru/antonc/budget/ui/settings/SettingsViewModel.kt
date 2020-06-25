package ru.antonc.budget.ui.settings

import ru.antonc.budget.repository.TransactionRepository
import ru.antonc.budget.ui.base.BaseViewModel
import javax.inject.Inject


class SettingsViewModel @Inject constructor(
    repository: TransactionRepository
) : BaseViewModel() {

}