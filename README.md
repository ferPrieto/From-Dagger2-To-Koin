# Android - Clean Architecture - From Dagger2 to Koin

This is a Dagger to [Koin][1] migration approach from Fernando Cejas's repository [Android-CleanArchitecture-Kotlin][2]

[1]:       https://insert-koin.io/docs/2.0/getting-started/android/
[2]:       https://github.com/android10/Android-CleanArchitecture-Kotlin

## Android 3 Layers Architecture and DI setup

Based on these three layers, I have created three modules for `Presentation`, `Domain` and `Data`. Also an additional `AppModule`
![https://fernandocejas.com/2018/05/07/architecting-android-reloaded/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture-Kotlin/architecture/clean_architecture_reloaded_layers.png)

##  DI ApplicationComponent migration
This was the Dagger2 implementation (original)
```
@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)

    fun inject(moviesFragment: MoviesFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)
}
```
There's no `DI Component` definition for Koin. I have defined how the Application class results after the migration in the section `Application migration`.
Within `Application` is where the modules used are included. That means, a class less to implement in our project.

##  Application migration
This was the Dagger2 implementation (original)
```
class AndroidApplication : Application() {
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        this.initializeLeakDetection()
    }
}
```

This is the Koin implementation, where the modules used must be listed.
```
class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, presentationModule, domainModule, dataModule))
        this.initializeLeakDetection()
    }
}
```

## DI Modules migration
As I explained in the beginning of the document, I have created three different modules, based on clean architecture layers (`PresentationModule`, `DomainModule`, `DataModule`), plus `AppModule`, in order to abstract the Navigator class used.

In order to show how much more concise and less verbose Koin is, I am going to show the `ViewModelModule` (original) and `PresentationModule` (koin version).

###Dagger2
```
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindsMoviesViewModel(moviesViewModel: MoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindsMovieDetailsViewModel(movieDetailsViewModel: MovieDetailsViewModel): ViewModel
}
```

###Koin

As you can see `ViewModelFactory` is not necessary any more, since Koin gives you the VM support (more info in `ViewModelFactory migration` section).
I have included other UI related classes (`MoviesAdapter`, `MovieDetailsAnimator`) and another one related to the VM (`Authenticator`).

```
val presentationModule = module {
    viewModel { MoviesViewModel() }

    viewModel { MovieDetailsViewModel() }

    single { MoviesAdapter() }

    single { MovieDetailsAnimator() }

    single { Authenticator() }
}
```

## ViewModelFactory migration
In the original version with Dagger2, the `ViewModelFactory` had to be implemented, in order to let the VM instace creation within the fragments. An then, being called in your fragments/activities for the VM creation.
This is the original code used within `onCreate` function, in `MoviesFragment`:
```
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appComponent.inject(this)

    moviesViewModel = viewModel(viewModelFactory) {
        observe(movies, ::renderMoviesList)
        failure(failure, ::handleFailure)
    }
}

```

In the koin version, the viewModel is declared as an inmutable object, outside of `onCreate`. And I have got rid off the `ViewModelFactory`, because `by viewmodel` is encharched to do the same.
```
private val movieDetailsViewModel by viewModel<MovieDetailsViewModel>()
private val successObserver = Observer<MovieDetailsView> { movieDetailsView ->
    renderMovieDetails(movieDetailsView)
}
private val failureObserver = Observer<Failure> { failure ->
    handleFailure(failure)
}
```

## AndroidX update

I have updated the main Android libraries, as I wanted to show this project updated to the lastest standards and APIs possible.


<a href="https://www.buymeacoffee.com/DSbMElGNU" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

## License

    Copyright 2019 Fernando Prieto Moyano

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
