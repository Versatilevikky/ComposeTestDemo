package com.gv.composetestdemo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Karthi on 06/07/2021.
 */

/** Sample Implementation :-
 *
 * <Inject ZSSnackBarProvider() in the respective view class>
 *
 * Box(modifier = Modifier.fillMaxSize()) {
 *      val snackbarHostState = remember { SnackbarHostState() }
 *      val scope = rememberCoroutineScope()
 *
 *      Button(onClick = {
 *          scope.launch {
 *              snackBarProvider.showSnackBar(
 *                  scope = scope,
 *                  snackbarHostState = snackbarHostState,
 *                  message = "Default Message",
 *                  duration = SnackbarDuration.Short,
 *                  actionLabel = "Action"
 *              )
 *          }
 *      }) {
 *          Text(text = "Click to show snack bar")
 *      }
 *      ZSSnackBar(
 *          snackbarHostState = snackbarHostState,
 *          onActionClick = {},
 *          modifier = Modifier.align(Alignment.BottomCenter)
 *      )
 * */


class SnackBarProvider  constructor() {

    private var snackBarJob: Job? = null

    init {
        cancelCurrentJob()
    }

    fun showSnackBar(
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration
    ) {
        if (snackBarJob == null) {
            snackBarJob = scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = duration,
                    actionLabel = actionLabel
                )
                cancelCurrentJob()
            }
        } else {
            cancelCurrentJob()
            snackBarJob = scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = duration,
                    actionLabel = actionLabel
                )
                cancelCurrentJob()
            }
        }
    }

    fun dismissSnackBar() {
        cancelCurrentJob()
    }

    /*To dismiss any snackbar if it is already being displayed*/
    private fun cancelCurrentJob() {
        snackBarJob?.let {
            it.cancel()
            snackBarJob = Job()
        }
    }
}

@Composable
private fun SnackBarHolder(
    snackbarHostState: SnackbarHostState,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { snackBarData ->
            Snackbar(
                modifier = Modifier
                    .padding(horizontal = 28.dp, vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                action = {
                    snackBarData.actionLabel?.let { label ->
                        Button(
                            onClick = { onActionClick() },
                        ) {
                            Text(
                                text = label,
                                color = Color.White
                            )
                        }
                    }
                },
                actionOnNewLine = actionOnNewLine,
                content = {
                    Text(
                        text = snackBarData.message,
                        color = Color.White
                    )
                },
            )
        },
        modifier = modifier
    )
}

@Composable
fun SnackBar(
    snackbarHostState: SnackbarHostState,
    onActionClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SnackBarHolder(
            modifier = Modifier.align(Alignment.BottomCenter),
            snackbarHostState = snackbarHostState,
            onActionClick = onActionClick,
        )
    }
}