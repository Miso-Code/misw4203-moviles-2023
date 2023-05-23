package com.example.misw4203moviles2023.ui.view

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.misw4203moviles2023.R
import com.example.misw4203moviles2023.databinding.FragmentAlbumAddTrackBinding
import com.example.misw4203moviles2023.domain.album.model.Track
import com.example.misw4203moviles2023.ui.viewModel.AlbumAddTrackViewModel
import com.google.android.material.textfield.TextInputEditText
import java.util.Locale

class AlbumAddTrack(private val viewModel: AlbumAddTrackViewModel? = null) :
    Fragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var _viewModel: AlbumAddTrackViewModel

    private var _binding: FragmentAlbumAddTrackBinding? = null
    private val binding get() = _binding!!

    private val args: AlbumAddTrackArgs by navArgs()

    private var actionBar: ActionBar? = null

    private lateinit var trackName: TextInputEditText
    private lateinit var trackDuration: TextInputEditText

    private lateinit var addTrackButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAlbumAddTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel = viewModel ?: ViewModelProvider(this)[AlbumAddTrackViewModel::class.java]
        _viewModel.onCreate(args.albumId)

        setupViews()
        setupTextWatchers()
        setupClickListeners()
        observeDataChanges()
    }

    private fun setupViews() {
        trackName = binding.trackNameEditText
        trackDuration = binding.trackDurationEditText
        addTrackButton = binding.trackCreateButton
    }

    private fun setupTextWatchers() {
        var isTrackNameValid = false
        var isTrackDurationValid = false

        class ValidationTextWatcher(private val validationCallback: () -> Unit) : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validationCallback.invoke()

                addTrackButton.isEnabled = isTrackNameValid && isTrackDurationValid
                addTrackButton.alpha = 1f
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }
        }

        trackName.addTextChangedListener(
            ValidationTextWatcher {
                isTrackNameValid = trackName.text.toString().isNotEmpty()
            },
        )

        trackDuration.addTextChangedListener(
            ValidationTextWatcher {
                isTrackDurationValid = trackDuration.text.toString().isNotEmpty()
            },
        )
    }

    private fun setupClickListeners() {
        trackDuration.setOnClickListener {
            showTimePicker()
        }
        addTrackButton.setOnClickListener {
            _viewModel.addTrack(
                Track(
                    id = -1,
                    name = trackName.text.toString(),
                    duration = trackDuration.text.toString(),
                ),
            )
        }
    }

    private fun observeDataChanges() {
        _viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            addTrackButton.isEnabled = !isLoading

            trackName.isEnabled = !isLoading
            trackDuration.isEnabled = !isLoading

            binding.trackCreateLoadingSpinner.visibility =
                if (isLoading) View.VISIBLE else View.GONE

            if (!isLoading) {
                Toast.makeText(requireContext(), R.string.track_create_success, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showTimePicker() {
        val timePickerDialog = TimePickerDialog(requireContext(), this, 0, 0, true)
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        // we are using hours as minutes because we are not using hours, minutes are seconds
        val hour = String.format(Locale.ROOT, "%02d", hourOfDay)
        val minutes = String.format(Locale.ROOT, "%02d", minute)
        trackDuration.setText("$hour:$minutes")
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        super.onActivityCreated(savedInstanceState)
        actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.title = getString(R.string.menu_add_track)
    }
}
