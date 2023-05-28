package com.example.misw4203moviles2023.ui.view

import android.app.DatePickerDialog
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.misw4203moviles2023.R
import com.example.misw4203moviles2023.databinding.FragmentAlbumCreateBinding
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.ui.viewModel.AlbumCreateViewModel
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Suppress("TooManyFunctions")
class AlbumCreate(private val viewModel: AlbumCreateViewModel? = null) :
    Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var _viewModel: AlbumCreateViewModel
    private var _binding: FragmentAlbumCreateBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressBarGenres: ProgressBar
    private lateinit var progressBarRecordLabels: ProgressBar

    private lateinit var releaseDateEditText: TextInputEditText
    private lateinit var coverUrl: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAlbumCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel = viewModel ?: ViewModelProvider(this)[AlbumCreateViewModel::class.java]
        _viewModel.onCreate()

        setupViews()
        setupTextWatchers()
        setupClickListeners()
        observeDataChanges()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        progressBarGenres = binding.genreLoadingSpinner
        progressBarRecordLabels = binding.recordLabelLoadingSpinner

        releaseDateEditText = binding.albumReleaseDateEditText
        coverUrl = binding.albumCoverEditText
    }

    private fun setupTextWatchers() {
        var isAlbumNameValid = false
        var isReleaseDateValid = false
        var isDescriptionValid = false
        var isGenreValid = false
        var isRecordLabelValid = false
        var isCoverValid = false

        class ValidationTextWatcher(private val validationCallback: () -> Unit) : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validationCallback.invoke()

                binding.albumCreateButton.isEnabled =
                    isAlbumNameValid && isReleaseDateValid && isDescriptionValid && isGenreValid &&
                    isRecordLabelValid && isCoverValid
                binding.albumCreateButton.alpha = 1f
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }
        }

        binding.albumNameEditText.addTextChangedListener(
            ValidationTextWatcher {
                isAlbumNameValid = binding.albumNameEditText.text.toString().isNotEmpty()
            },
        )

        binding.albumRecordLabelEditText.addTextChangedListener(
            ValidationTextWatcher {
                isRecordLabelValid = binding.albumRecordLabelEditText.text.toString()
                    .isNotEmpty() && _viewModel.recordLabels.value?.contains(
                    binding.albumRecordLabelEditText.text.toString(),
                ) == true
            },
        )

        binding.albumDescriptionEditText.addTextChangedListener(
            ValidationTextWatcher {
                isDescriptionValid = binding.albumDescriptionEditText.text.toString().isNotEmpty()
            },
        )

        binding.albumGenreEditText.addTextChangedListener(
            ValidationTextWatcher {
                isGenreValid = binding.albumGenreEditText.text.toString()
                    .isNotEmpty() && _viewModel.genres.value?.contains(
                    binding.albumGenreEditText.text.toString(),
                ) == true
            },
        )

        coverUrl.addTextChangedListener(
            ValidationTextWatcher {
                val url = coverUrl.text.toString()
                isCoverValid = isValidUrl(url)

                if (isCoverValid) {
                    Glide.with(requireContext()).load(url)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                isFirstResource: Boolean,
                            ): Boolean {
                                isCoverValid = false
                                return true
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean,
                            ): Boolean {
                                isCoverValid = true
                                return false
                            }
                        }).placeholder(R.drawable.ic_album).into(binding.albumCoverPreview)
                }
            },
        )

        releaseDateEditText.addTextChangedListener(
            ValidationTextWatcher {
                isReleaseDateValid = releaseDateEditText.text.toString().isNotEmpty()
            },
        )
    }

    private fun setupClickListeners() {
        releaseDateEditText.setOnClickListener {
            showDatePicker()
        }

        binding.albumCreateButton.setOnClickListener {
            _viewModel.createAlbum(
                Album(
                    id = -1,
                    name = binding.albumNameEditText.text.toString(),
                    description = binding.albumDescriptionEditText.text.toString(),
                    releaseDate = releaseDateEditText.text.toString(),
                    genre = binding.albumGenreEditText.text.toString(),
                    recordLabel = binding.albumRecordLabelEditText.text.toString(),
                    cover = coverUrl.text.toString(),
                    tracks = emptyList(),
                ),
            )
        }
    }

    private fun observeDataChanges() {
        progressBarGenres.visibility = View.VISIBLE
        _viewModel.genres.observe(viewLifecycleOwner) { genres ->
            val genresField = binding.albumGenreEditText
            val genresAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                genres.orEmpty(),
            )
            genresField.setAdapter(genresAdapter)
            progressBarGenres.visibility = View.GONE
        }

        progressBarRecordLabels.visibility = View.VISIBLE
        _viewModel.recordLabels.observe(viewLifecycleOwner) { recordLabels ->
            val recordLabelsField = binding.albumRecordLabelEditText
            val recordLabelsAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                recordLabels.orEmpty(),
            )
            recordLabelsField.setAdapter(recordLabelsAdapter)
            progressBarRecordLabels.visibility = View.GONE
        }

        _viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.albumCreateButton.isEnabled = !isLoading

            binding.albumNameEditText.isEnabled = !isLoading
            binding.albumDescriptionEditText.isEnabled = !isLoading
            binding.albumReleaseDateEditText.isEnabled = !isLoading
            binding.albumGenreEditText.isEnabled = !isLoading
            binding.albumRecordLabelEditText.isEnabled = !isLoading
            binding.albumCoverEditText.isEnabled = !isLoading

            binding.albumCreateLoadingSpinner.visibility =
                if (isLoading) View.VISIBLE else View.GONE

            if (!isLoading) {
                Toast.makeText(requireContext(), R.string.album_create_success, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isValidUrl(urlString: String?): Boolean {
        val uri = Uri.parse(urlString)
        // Use the scheme-related methods of Uri class to check if it is a valid URL
        val uriScheme = uri.scheme?.lowercase()
        return uri != null && (uriScheme == "http" || uriScheme == "https")
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        datePickerDialog.datePicker.maxDate =
            System.currentTimeMillis() // Optional: Set a maximum date
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)

        releaseDateEditText.setText(formattedDate)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        super.onActivityCreated(savedInstanceState)
        val actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.title = getString(R.string.menu_album_create)
    }
}
