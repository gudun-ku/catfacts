package com.beloushkin.test.catfacts

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxTextSchedulerRule(
    private val testScheduler: TestScheduler = TestScheduler()): Scheduler(),
    TestRule {

    override fun apply(base: Statement, description: Description?): Statement {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        RxJavaPlugins.setNewThreadSchedulerHandler { testScheduler }
        RxJavaPlugins.setSingleSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        return base
    }

    override fun createWorker(): Worker = testScheduler.createWorker()

    fun triggerActions() = testScheduler.triggerActions()

}

