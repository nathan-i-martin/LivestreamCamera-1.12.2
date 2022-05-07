# LivestreamCamera | Minecraft 1.12.2

Configure pre-set positions and switch between them using hotbar slots.

## Configuration and Usage

### Example configuration:
```yaml
0:
  x: -156.0
  y: 75.0
  z: 30.0
  pitch: 10.0
  yaw: -80.0
```

Hotbar slots are saved in index format. this means they start at `0` and count to `8`.
### Commands
`/cam`
> `/cam on` - Enter cam mode. Only one user should be in cam mode at a time.<br>
> `/cam off` - Exit cam mode<br>
> `/cam zoom <amount>` - Set a zoom level! Set between -5 and 5! Positive numbers zoom in, negative numbers zoom out. (tbh this command doesn't work super well)

### Controls
Control your camera position by selecting a hotbar slot! If your camera operator has an RGB keyboard or Streamdeck,
they might find it helpful to assign custom colors or icons to specific slots.
