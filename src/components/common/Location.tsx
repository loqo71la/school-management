import { useMemo, useRef, useState } from 'react';
import { MapContainer, Marker, TileLayer } from 'react-leaflet';
import { Icon, LatLng } from 'leaflet';

import markerIconPng from 'leaflet/dist/images/marker-icon.png'

const icon = new Icon({ iconUrl: markerIconPng, iconSize: [25, 41], iconAnchor: [12, 41] });

type LocationProps = {
  value: { lat: number, lng: number };
  onChange: (point: LatLng) => void;
}

function Location({ value, onChange }: LocationProps) {
  const [position, setPosition] = useState<LatLng>(new LatLng(value.lat, value.lng));
  const markerRef = useRef<L.Marker>(null);

  const handleMove = useMemo(() => ({
    dragend() {
      const point = markerRef.current?.getLatLng();
      if (!point) return

      setPosition(point);
      onChange(point);
    }
  }), [onChange]);

  return (
    <section className="my-3">
      <p className="text-sm items-center">
        Latitude:
        <span className="mx-2 text-base font-light">{position.lat.toFixed(6)}</span>
        Longitude:
        <span className="ml-2 text-base font-light">{position.lng.toFixed(6)}</span>
      </p>
      <MapContainer center={position} zoom={13} scrollWheelZoom={false} style={{ height: 350 }}>
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        <Marker
          ref={markerRef}
          draggable={true}
          position={position}
          eventHandlers={handleMove}
          icon={icon}>
        </Marker>
      </MapContainer>
    </section>
  );
}

export default Location;
